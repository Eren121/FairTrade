package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.entity.Player;

public abstract class AbstractOfferEvent extends AbstractTransactionEvent {

    protected Offer offer;
    protected int id;

    public AbstractOfferEvent(Trade trade, Offer offer) {
        super(trade);
        this.offer = offer;
        this.id = (offer == trade.getOffer(0) ? 0 : 1);
    }

    public Offer getOffer() {
        return offer;
    }


    @Override
    public final Player getPlayer() {
        return trade.getOffer(id).getPlayer();
    }

    @Override
    public final Player getOther() {
        return trade.getOffer(1 - id).getPlayer();
    }
}
