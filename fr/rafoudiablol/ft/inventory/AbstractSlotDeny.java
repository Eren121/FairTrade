package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.utils.inv.AbstractSlotType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

public class AbstractSlotDeny extends AbstractSlotType {

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot) {
        return false;
    }
}
