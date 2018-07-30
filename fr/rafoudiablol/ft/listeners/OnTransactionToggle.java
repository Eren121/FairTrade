package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.ToggleTransactionEvent;
import org.bukkit.event.Listener;

public interface OnTransactionToggle extends Listener {

    @SuppressWarnings("unused")
    void onTransactionToggle(ToggleTransactionEvent e);
}
