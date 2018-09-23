package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.entity.Player;

public abstract class AbstractOfferEvent extends AbstractTransactionEvent {

    protected Offer offer;

    public AbstractOfferEvent(Trade trade, Offer offer) {
        super(trade);
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }
}
