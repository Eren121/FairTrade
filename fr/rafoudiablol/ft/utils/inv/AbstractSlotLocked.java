package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public abstract class AbstractSlotLocked extends AbstractSlot {

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {
        return false;
    }
}
