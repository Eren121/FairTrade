package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.event.HandlerList;

/**
 * Fire when a player don't want to trade anymore (disconnect, close gui)
 */
public class AbortTransactionEvent extends AbstractTransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    public AbortTransactionEvent(Trade trade) {
        super(trade);
    }
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
        return handlers;
    }
}
