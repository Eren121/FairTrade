package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class SlotLocked extends AbstractSlotLocked {

    public static final int ID = -1;
    @Override
    public int getId() {
        return ID;
    }
}
