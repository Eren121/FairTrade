package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;

public interface OnTransactionAbort {

    @SuppressWarnings("unused")
    void onTransactionAbort(AbortTransactionEvent e);
}
