package fr.rafoudiablol.ft.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface OnInventoryClose extends Listener {

    @SuppressWarnings("unused")
    void onInventoryClose(InventoryCloseEvent e);
}
