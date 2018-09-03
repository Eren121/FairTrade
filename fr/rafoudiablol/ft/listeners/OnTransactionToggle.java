package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionToggle extends Listener {

    @SuppressWarnings("unused")
    void onTransactionToggle(StatusTransactionEvent e);
}
