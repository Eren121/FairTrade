package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Fire when a player send a transaction request to another player.
 * If cancelled, the request is ignored and never saw by the other player
 */
public class RequestTransactionEvent extends AbstractTransactionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    public RequestTransactionEvent(Player p1, Player p2) {
        super(new Trade(p1, p2));
    }
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
            return handlers;
        }

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}