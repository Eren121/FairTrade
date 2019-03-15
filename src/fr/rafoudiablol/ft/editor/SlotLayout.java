package fr.rafoudiablol.ft.editor;

import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.ISlot;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SlotLayout implements ISlot {

    protected AbstractSkeleton skeleton;

    public SlotLayout(AbstractSkeleton skeleton) {

        this.skeleton = skeleton;
    }

    @Override
    public ItemStack getDefault(int i) {
        return skeleton.get(i).getDefault(i);
    }

    @Override
    public boolean action(InventoryClickEvent e) {
        return false;
    }
}
