package fr.rafoudiablol.ft.utils;

import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Inventoris {

    /**
     * Add to player inventory all items in the slots that matches getClass() == clazz, and clear inventory
     * Keep items in inventory if they cannot be stored
     *
     * @param player corresponding player
     * @return an array of all matches items, including what it cannot be stored
     */
    public static ItemStack[] takeAll(Class<? extends AbstractSlot> clazz, HumanEntity player)
    {
        Inventory inv = player.getOpenInventory().getTopInventory();
        AbstractSkeleton sk = Holder.tryGet(inv.getHolder());
        Inventory playerInv = player.getInventory();
        int size = inv.getSize();

        // Get all owner items

        ItemStack[] remotes = new ItemStack[size];

        for(int slot : sk.byType(clazz)) {

            remotes[slot] = inv.getItem(slot);
        }

        inv.clear();

        // Keep the item when the player has no empty space

        Map<Integer, ItemStack> remaining = playerInv.addItem(remotes);
        remaining.forEach(inv::setItem);

        // Remove null items

        remotes = Arrais.removeNullFromArray(remotes);
        return remotes;
    }
}
