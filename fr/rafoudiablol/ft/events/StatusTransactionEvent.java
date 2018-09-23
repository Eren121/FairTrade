package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;

/**
 * Fire when a player click on confirmation button to switch his status
 * (from confirm to cancel or from cancel to confirm)
 */
public class StatusTransactionEvent extends AbstractOfferEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean status;
    private Inventory inv;

    public StatusTransactionEvent(Trade trade, Offer offer) {
        super(trade, offer);
        this.inv = offer.getPlayer().getOpenInventory().getTopInventory();
        this.status = offer.getConfirm();
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

    public boolean hasConfirm() {
        return status;
    }
}
