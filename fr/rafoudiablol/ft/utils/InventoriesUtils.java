package fr.rafoudiablol.ft.utils;

import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class InventoriesUtils {

    /**
     * Merge two inventories.
     * If dest has not enough place, items are keep.
     *
     * @param filter filter for source inventory slots
     * @param dest
     * @param source
     * @return an array of all matches items, including what it cannot be stored
     */
    public static ItemStack[] merge(Class<? extends AbstractSlot> filter, Inventory dest, Inventory source)
    {
        ArrayList<ItemStack> ret = new ArrayList<>();
        AbstractSkeleton sk = Holder.tryGet(source.getHolder());
        int size = source.getSize();

        for(int i = 0; i < size; ++i) {

            ItemStack stack = source.getItem(i);
            ItemStack newItem = null;

            if(filter.equals(sk.get(i).getClass())) {

                if(stack != null) {

                    ret.add(stack);

                    Map<Integer, ItemStack> remain = dest.addItem(stack);
                    if(!remain.isEmpty()) {

                        // Maximum one item, because we add only one
                        source.setItem(i, remain.get(0));
                        newItem = remain.get(0);
                    }
                }
            }

            source.setItem(i, newItem);
        }

        return ret.toArray(new ItemStack[0]);
    }
}
