package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.Offer;
import org.bukkit.entity.Player;

public abstract class AbstractOfferEvent extends AbstractTransactionEvent {

    protected Offer offer;

    public AbstractOfferEvent(Player bully, Player victim, Offer offer) {
        super(bully, victim);
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer;
    }
}
