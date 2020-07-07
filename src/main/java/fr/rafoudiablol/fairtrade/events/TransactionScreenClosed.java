package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player close his trading GUI.
 * Also called two time, one for each player for one trade.
 */
public class TransactionScreenClosed extends AbstractTransactionEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public TransactionScreenClosed(Transaction transaction, Player player) {
        super(transaction);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
