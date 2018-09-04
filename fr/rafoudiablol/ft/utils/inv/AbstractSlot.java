package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSlot {

    private AbstractSkeleton skeleton;

    /**
     * all slots types must be unique in same inventory
     */
    public abstract int getId();

    /**
     * @return false is the slot is not modifiable, otherwise true
     */
    public abstract boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click);

    /**
     * @return Default item in this slot
     */
    public ItemStack getDefault() {
        return null;
    }

    public final void setSkeleton(AbstractSkeleton s) {
        skeleton = s;
    }

    public final AbstractSkeleton getSkeleton() {
        return skeleton;
    }
}
