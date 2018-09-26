package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.event.HandlerList;

/**
 * <p>
 * This event is fired when one player of the trade close his trade GUI.
 * It can be for various reasons:
 * <ul>
 *     <li>He don't want to trade anymore and declines the offer
 *     <li>He was disconnected while he was trading with another player
 * </ul>
 *
 * <p>
 * Known listeners:
 * <ul>
 *     <li>{@link fr.rafoudiablol.ft.listeners.CloseRemoteInventory}
 *     <li>{@link fr.rafoudiablol.ft.listeners.TradeTracker}
 * </ul>
 *
 * @see fr.rafoudiablol.ft.listeners.CloseRemoteInventory
 * @see fr.rafoudiablol.ft.listeners.TradeTracker
 */
public class AbortTransactionEvent extends AbstractTransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    public AbortTransactionEvent(Trade trade) {
        super(trade);
    }

    /** Generic bukkit implementation */
    @Override public HandlerList getHandlers() {
        return handlers;
    }

    /** Generic bukkit implementation */
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
        return handlers;
    }
}
