package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.ITransactionLink;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Base event for FairTrade events
 */
public abstract class TransactionEvent extends Event implements ITransactionLink {

    protected Player player, other;

    public TransactionEvent(Player bully, Player victim)
    {
        this.player = bully;
        this.other = victim;
    }

    @Override
    public final Player getPlayer() {
        return player;
    }

    @Override
    public final Player getOther() {
        return other;
    }
}
