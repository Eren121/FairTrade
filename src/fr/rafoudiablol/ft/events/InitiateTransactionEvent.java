package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Fire when a trade transaction begin (opening of the GUI)
 * If cancelled, the transaction is aborted
 */
public class InitiateTransactionEvent extends AbstractTransactionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public InitiateTransactionEvent(Trade trade) {
        super(trade);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getAsker() { return trade.getOffer(0).getPlayer(); }
    public Player getReplier() { return trade.getOffer(1).getPlayer(); }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
