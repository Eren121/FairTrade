package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AcceptedTransactionEvent extends AbstractTransactionEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private String cancelReason = "";
    private boolean cancelled = false;

    public AcceptedTransactionEvent(Transaction transaction) {
        super(transaction);
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String reason) {
        cancelReason = reason;
        cancelled = (reason != null && !reason.isEmpty());
    }
}