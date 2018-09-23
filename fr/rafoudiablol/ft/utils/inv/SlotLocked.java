package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SlotLocked extends AbstractSlot {

    @Override
    public boolean action(InventoryClickEvent e) {
        return false;
    }
}
