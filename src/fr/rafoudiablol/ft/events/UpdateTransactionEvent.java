package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Fire when a player change a trade item
 */
public class UpdateTransactionEvent extends AbstractOfferEvent {

    private static final HandlerList handlers = new HandlerList();
    private Inventory inv;
    private int slot;

    public UpdateTransactionEvent(Trade trade, Offer offer, int slot) {
        super(trade, offer);
        this.inv = offer.getPlayer().getOpenInventory().getTopInventory();
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

    public Inventory getInventory() {
        return inv;
    }

    public int getSlot() {
        return slot;
    }
}
