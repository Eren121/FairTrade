package fr.rafoudiablol.ft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class RequestTransactionEvent extends TransactionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    public RequestTransactionEvent(Player bully, Player victim) {
        super(bully, victim);
    }
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
            return handlers;
        }

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}