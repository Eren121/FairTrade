package fr.rafoudiablol.ft.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface OnInventoryClick extends Listener {

    @SuppressWarnings("unused")
    void onInventoryClick(InventoryClickEvent e);
}
