package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionRequest extends Listener {

    @SuppressWarnings("unused")
    void onTransactionRequest(RequestTransactionEvent e);
}
