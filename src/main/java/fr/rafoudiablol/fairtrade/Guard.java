package fr.rafoudiablol.fairtrade;

import fr.rafoudiablol.fairtrade.events.TransactionScreenClosed;
import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.internationalization.Context;
import fr.rafoudiablol.internationalization.MessagePart;
import fr.rafoudiablol.internationalization.Variable;
import fr.rafoudiablol.fairtrade.events.AskForTransaction;
import fr.rafoudiablol.fairtrade.events.ChangeOffer;
import fr.rafoudiablol.fairtrade.events.ToggleConfirmation;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.slots.ItemSlot;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The role of this class is to prevent all sort of ingame bugs, hacks, or exploits possibles.
 * Some bugs could exists but this is the main role of this class to prevent them.
 * If this class is removed, multiple bugs were possibles.
 */
public class Guard implements Listener {
    private final FairTrade plugin;

    public Guard(FairTrade plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    /**
     * Prevent to pick up items when a player is trading.
     * It is to prevent the possibility to take items and there would be not enough space to take them back when
     * GUI closes.
     */
    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            if(plugin.transactionManager.isTrading(player)) {
                e.setCancelled(true);
            }
        }
    }

    /**
     * Put back to the player's inventory all items from his offer when the trade was aborted.
     * If there is not enough place (it should be because any pick up were cancelled), for example by another
     * special plugin event or by another person making /give command, the items will be dropped.
     * Note that it's the same behavior as when closing a crafting table, so it's perfectly ok, and this normally
     * should not happen in a normal game (unless everybody is op...).
     */
    @EventHandler
    public void onClose(TransactionScreenClosed e) {
        final TransactionScreen screen = plugin.transactionScreen;
        final Player player = e.getPlayer();
        final Inventory inventory = player.getOpenInventory().getTopInventory();

        for(int i = 0; i < screen.length(); i++) {
            if(screen.getSlot(i) instanceof ItemSlot) {
                final ItemSlot slot = (ItemSlot)screen.getSlot(i);
                if(slot.getSide() == Side.LOCAL) {
                    final ItemStack item = inventory.getItem(slot.getRawSlot());

                    if(item != null) {
                        inventory.setItem(slot.getIndex(), null);
                        for(ItemStack didntFit : player.getInventory().addItem(item).values()) {
                            player.getWorld().dropItemNaturally(player.getLocation(), didntFit);
                        }
                    }
                }
            }
        }


        inventory.clear();
    }

    /**
     * Disable both offer when an offer changes
     */
    @EventHandler
    public void onChange(ChangeOffer e) {
        final Transaction transaction = e.getTransaction();

        if(transaction.getInitiator().hasConfirmed() || transaction.getReplier().hasConfirmed()) {

            transaction.getInitiator().setConfirmed(false);
            transaction.getReplier().setConfirmed(false);

            final ToggleConfirmation event = new ToggleConfirmation(transaction);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    /**
     * Notify player
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsk(AskForTransaction e) {
        final ComponentBuilder builder = new ComponentBuilder();
        final Context context = plugin.messages.ask.getContext(e.initiator.getDisplayName(), plugin.messages.accept.translate(), plugin.messages.refuse.translate());

        for(MessagePart part : plugin.messages.ask.getParts()) {
            if(part instanceof Variable) {
                final TextComponent component = new TextComponent(part.translate(context));
                final Variable variable = (Variable)part;

                if(variable.getIndex() == 1) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/trade " + e.initiator.getDisplayName()));
                }
                else if(variable.getIndex() == 2) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));
                }

                builder.append(component);
            }
            else {
                builder.append(part.translate(context));
            }
        }

        e.replier.spigot().sendMessage(builder.create());
    }
}
