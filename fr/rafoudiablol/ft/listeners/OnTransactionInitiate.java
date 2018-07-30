package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionInitiate extends Listener {

    @SuppressWarnings("unused")
    void onInitiateTransaction(InitiateTransactionEvent e);
}
