package fr.rafoudiablol.ft.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

public class ToggleTransactionEvent extends TransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean status;
    private HumanEntity src;
    private Inventory inv;
    private int slot;

    /**
     * @param player the player who toggle his status
     * @param other the other player in trading inventory
     * @param inv the trading inventory
     * @param status the new status of 'player'
     */
    public ToggleTransactionEvent(Player player, Player other, Inventory inv, boolean status) {
        super(player, other);
        this.src = player;
        this.inv = inv;
        this.status = status;
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

    public boolean hasConfirm() {
        return status;
    }
}
