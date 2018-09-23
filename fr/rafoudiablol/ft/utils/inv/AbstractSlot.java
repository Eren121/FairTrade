package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSlot {

    /**
     * @return false is the slot is not modifiable, otherwise true
     */
    public abstract boolean action(InventoryClickEvent e);

    /**
     * @return Default item in this slot
     */
    public ItemStack getDefault() {
        return null;
    }
}
