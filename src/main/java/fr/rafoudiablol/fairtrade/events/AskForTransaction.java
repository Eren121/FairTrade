package fr.rafoudiablol.fairtrade.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AskForTransaction extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public final Player initiator;
    public final Player replier;
    private boolean cancelled;
    private String reason;

    public AskForTransaction(Player initiator, Player replier) {
        this.initiator = initiator;
        this.replier = replier;
        this.cancelled = false;
    }

    public void setCancellationReason(String reason) {
        this.reason = reason;
    }

    public String getCancellationReason() {
        return reason;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
