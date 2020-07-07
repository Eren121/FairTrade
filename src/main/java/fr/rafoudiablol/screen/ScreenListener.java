package fr.rafoudiablol.screen;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;

public class ScreenListener implements Listener {
    private final ActionHelper helper = new ActionHelper();
    private Set<Player> players = new HashSet<>();
    private final Screen screen;

    public boolean isManaged(HumanEntity player) {
        return player instanceof Player && players.contains(player);
    }

    public boolean isManaged(InventoryInteractEvent e) {
        return isManaged(e.getWhoClicked());
    }

    ScreenListener(Screen screen) {
        this.screen = screen;
    }

    public void onOpen(Player player) {
        players.add(player);
    }

    protected boolean acceptEvent(InventoryAction action, int rawSlot, HumanEntity whoClicked) {

        if(!helper.concernOnlyCurrentSlot(action)) {
            return false;
        }

        if(!helper.hasSlot(action)) {
            return true;
        }

        if(!screen.doesRawSlotBelongsToCustomInventory(rawSlot)) {
            return true;
        }

        final Slot slot = screen.getSlot(rawSlot);

        if(action == InventoryAction.CLONE_STACK) {
            return slot.isCloneable();
        }
        else {
            return slot.isWritable();
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if(isManaged(e)) {

            if(!acceptEvent(e.getAction(), e.getRawSlot(), e.getWhoClicked())) {
                e.setCancelled(true);
            }

            // RUN TASK AT NEXT TICK
            // Because if some event change the gui, but the gui is already currently changing.
            // Like update items
            Bukkit.getScheduler().runTask(screen.getPlugin(), () -> {
                if (screen.doesRawSlotBelongsToCustomInventory(e.getRawSlot())) {
                    screen.getSlot(e.getRawSlot()).onEvent(e, e.getAction(), e.getWhoClicked());
                }
            });
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if(isManaged(e)) {
            final boolean invalided = e.getRawSlots().stream().anyMatch((rawSlot) ->
                screen.doesRawSlotBelongsToCustomInventory(rawSlot) && !screen.getSlot(rawSlot).isWritable()
            );

            if(invalided) {
                e.setCancelled(true);
            }

            Bukkit.getScheduler().runTask(screen.getPlugin(), () -> {
               e.getRawSlots().forEach(rawSlot -> {
                   if(screen.doesRawSlotBelongsToCustomInventory(rawSlot)) {
                       screen.getSlot(rawSlot).onEvent(e, e.getWhoClicked());
                   }
               });
            });
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getPlayer() instanceof Player) {
            final Player player = (Player)e.getPlayer();

            if(players.contains(player)) {
                screen.onClosed((Player) e.getPlayer());
                players.remove(player);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        players.remove(e.getPlayer());
    }
}