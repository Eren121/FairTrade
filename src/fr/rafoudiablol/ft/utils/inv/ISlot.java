package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface ISlot {

    /**
     * @return false is the slot is not modifiable, otherwise true
     */
    boolean action(InventoryClickEvent e);

    /**
     * @return Default item in this slot
     * @param i slot index
     */
    default ItemStack getDefault(int i) {
        return null;
    }
}
