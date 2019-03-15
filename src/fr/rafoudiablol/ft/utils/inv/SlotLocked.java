package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.event.inventory.InventoryClickEvent;

public class SlotLocked implements ISlot {

    @Override
    public boolean action(InventoryClickEvent e) {
        return false;
    }
}
