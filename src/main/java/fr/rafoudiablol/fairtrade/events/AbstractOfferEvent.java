package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.Transaction;

public abstract class AbstractOfferEvent extends AbstractTransactionEvent {
    protected final Offer offer;

    public AbstractOfferEvent(Transaction transaction, Offer offer) {
        super(transaction);
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }
}
