package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionAccept extends Listener {

    @SuppressWarnings("unused")
    void onAcceptTransaction(FinalizeTransactionEvent e);
}
