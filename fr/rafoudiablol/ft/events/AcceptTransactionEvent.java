package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Fire when a player type an /accept command
 */
public class AcceptTransactionEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Override public HandlerList getHandlers() {
        return handlers;
    }
    @SuppressWarnings("unused") public static HandlerList getHandlerList() {
        return handlers;
    }

    protected Player players[];

    public AcceptTransactionEvent(Player commandSrc) {
        players = new Player[] {null, commandSrc};
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    /**
     * @param p the player targeted by /accept command
     */
    public void setTarget(Player p) {
        players[0] = p;
    }
}