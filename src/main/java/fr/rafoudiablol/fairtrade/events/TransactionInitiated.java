package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TransactionInitiated extends AbstractTransactionEvent {
    private static final HandlerList handlers = new HandlerList();

    public TransactionInitiated(Transaction transaction) {
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
}
