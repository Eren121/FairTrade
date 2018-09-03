package fr.rafoudiablol.ft.utils;

import fr.rafoudiablol.ft.main.FairTrade;
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
     * And optionally close inventory if all items can be taken
     *
     * @param player corresponding player
     * @param close true to close the inventory. If the player has not enough space, the inventory is never closed, even if close == true
     * @return an array of all matches items, including what it cannot be stored
     */
    public static ItemStack[] takeAll(Class<? extends AbstractSlot> clazz, HumanEntity player, boolean close)
    {
        Inventory inv = player.getOpenInventory().getTopInventory();
        AbstractSkeleton sk = Holder.tryGet(inv.getHolder());
        Inventory playerInv = player.getInventory();
        int size = inv.getSize();

        // Get all owner items

        ItemStack[] remotes = new ItemStack[size];

        for(int slot : sk.fromType(clazz)) {

            remotes[slot] = inv.getItem(slot);
        }

        inv.clear();

        // Keep the item when the player has no empty space

        Map<Integer, ItemStack> remaining = playerInv.addItem(remotes);
        remaining.forEach(inv::setItem);

        // Optionally close the inventory

        remotes = Arrais.removeNullFromArray(remotes);

        if(remotes.length > 0 && close) {

            FairTrade.getFt().taskAtNextTick(player::closeInventory);
        }

        return remotes;
    }
}
