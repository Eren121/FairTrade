package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.ITransactionLink;
import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Base event for FairTrade events
 */
public abstract class AbstractTransactionEvent extends Event implements ITransactionLink {

    protected Trade trade;

    public AbstractTransactionEvent(Trade trade) {
        this.trade = trade;
    }

    @Override
    public final Player getPlayer() {
        return trade.getOffer(0).getPlayer();
    }

    @Override
    public final Player getOther() {
        return trade.getOffer(1).getPlayer();
    }

    public Trade getTrade() {
        return trade;
    }
}
