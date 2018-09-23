package fr.rafoudiablol.ft.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Fire when a player change a trade item
 */
public class UpdateTransactionEvent extends AbstractTransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    private HumanEntity src;
    private Inventory inv;
    private int slot;

    public UpdateTransactionEvent(Player player, Player other, Inventory inv, int slot) {
        super(player, other);
        this.src = player;
        this.inv = inv;
        this.slot = slot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HumanEntity getWhoClicked() {
        return src;
    }

    public Inventory getInventory() {
        return inv;
    }

    public int getSlot() {
        return slot;
    }
}
