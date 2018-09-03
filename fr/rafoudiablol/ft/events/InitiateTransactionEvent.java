package fr.rafoudiablol.ft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Fire when a trade transaction begin (opening of the GUI)
 * If cancelled, the transaction is aborted
 */
public class InitiateTransactionEvent extends TransactionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public InitiateTransactionEvent(Player bully, Player victim) {
        super(bully, victim);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getAsker() { return this.player; }
    public Player getReplier() { return this.other; }
    public void setAsker(Player asker) {
        this.player = asker;
    }
    public void setReplier(Player replier) {
        this.other = replier;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
