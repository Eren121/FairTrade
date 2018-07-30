package fr.rafoudiablol.ft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class AbortTransactionEvent extends TransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    public AbortTransactionEvent(Player bully, Player victim) {
        super(bully, victim);
    }
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
        return handlers;
    }
}
