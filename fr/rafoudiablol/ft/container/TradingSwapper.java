package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.container.Skeleton;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.Arrais;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fr.rafoudiablol.ft.container.Skeleton.*;

public class TradingSwapper {

    /**
     *
     * @param player the player who takes items
     * @param inv the trading inventory
     * @return the taken items as a list
     */
    public List<ItemStack> takeRemoteAndClear(HumanEntity player, Inventory inv)
    {
        ItemStack[] storage = inv.getStorageContents();
        List<ItemStack> items = new ArrayList<>();

        for(int i = 0; i < INVENTORY.length; ++i)
        {
            if(INVENTORY[i] == Locations.Right.id && storage[i] != null)
            {
                items.add(storage[i]);
            }
        }

        takeRemoteAndClear(player, inv, items.toArray(new ItemStack[0]));
        return items;
    }

    private void takeRemoteAndClear(HumanEntity player, Inventory inv, ItemStack[] items)
    {
        Map<Integer, ItemStack> couldNotStore = player.getInventory().addItem(items);
        inv.clear();
        inv.addItem(couldNotStore.values().toArray(new ItemStack[0]));
    }

    public void closeInventory(HumanEntity player)
    {
        Inventory inv = player.getOpenInventory().getTopInventory();
        Inventory pinv = player.getInventory();
        Map<Integer, ItemStack> res = pinv.addItem(Arrais.removeNullFromArray(Skeleton.getOwnerItems(inv)));
        inv.clear();

        if(res.isEmpty())
        {
            FairTrade.getFt().taskAtNextTick(player::closeInventory);
        }
        else
        {
            inv.addItem(res.values().toArray(new ItemStack[0]));
        }
    }
}
