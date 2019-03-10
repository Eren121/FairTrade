package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.event.inventory.InventoryClickEvent;

public class SlotLocked extends AbstractSlot {

    @Override
    public boolean action(InventoryClickEvent e) {
        return false;
    }
}
