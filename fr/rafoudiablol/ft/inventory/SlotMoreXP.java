package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class SlotMoreXP extends AbstractSlotLocked {

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot) {

        return false;
    }
}
