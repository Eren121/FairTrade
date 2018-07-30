package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.AcceptTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionAccept extends Listener {

    @SuppressWarnings("unused")
    void onAcceptTransaction(AcceptTransactionEvent e);
}
