package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.event.Event;

public abstract class AbstractTransactionEvent extends Event {
    protected final Transaction transaction;

    public AbstractTransactionEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}