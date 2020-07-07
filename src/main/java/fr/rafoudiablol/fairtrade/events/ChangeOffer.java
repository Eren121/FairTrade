package fr.rafoudiablol.fairtrade.events;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Not that toggling the confirmation button do not fire this event, because this event is <change offer>,
 * and toggling confirmation button does not change the offer.
 *
 * This is used to update remote items view, and also to cancel all confirmation from both players, because
 * else it would be possible to hack (the remote player confirm, the local player remove all items and then confirm).
 */
public class ChangeOffer extends AbstractTransactionEvent {
    private final Type type;

    /**
     * Possibles change of the offer
     */
    public enum Type {
        /**
         * The player has changed his offer as concrete item
         */
        ITEM,

        /**
         * The player has changed his offer of a custom resource (a number).
         * This resource comes from external add-ons,
         * some built-in with the core are: XP, money.
         */
        RESOURCE
    }

    private static final HandlerList handlers = new HandlerList();

    public ChangeOffer(Transaction transaction, Type type) {
        super(transaction);
        this.type = type;
    }

    public Type getType() {
        return type;
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
