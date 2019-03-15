package fr.rafoudiablol.ft.editor;

import fr.rafoudiablol.ft.config.EnumEditableItems;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.ISlot;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SlotItem implements ISlot {

    protected AbstractSkeleton skeleton;
    protected EnumEditableItems item;

    public SlotItem(AbstractSkeleton skeleton, EnumEditableItems item) {

        this.skeleton = skeleton;
        this.item = item;
    }

    @Override
    public ItemStack getDefault(int i) {
        return item.getDefaultItem();
    }

    @Override
    public boolean action(InventoryClickEvent e) {
        return false;
    }
}
