package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.inventory.SlotLocal;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.ArraysUtils;
import fr.rafoudiablol.ft.utils.inv.Holder;
import net.minecraft.server.v1_13_R2.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * Make sure when a player close a trade inventory, the other close to, and keep items into player inventory
 */
public class CloseRemoteInventory implements Listener {

    @EventHandler
    public void event(AbortTransactionEvent e) {

        e.forEach(p -> {

            clearNonLocal(p);
            keep(p);
            close(p);
        });
    }

    @EventHandler
    public void event(FinalizeTransactionEvent e) {

        e.forEach(p -> {

            clearNonLocal(p);
            keep(p);
            close(p);
        });
    }

    private void clearNonLocal(Player p) {

        Inventory inventory = p.getOpenInventory().getTopInventory();

        for(int i = 0; i < inventory.getSize(); ++i) {

            if(!(SkeletonTrade.instance.get(i) instanceof SlotLocal)) {

                inventory.clear(i);
            }
        }
    }

    private void keep(Player p) {

        Inventory inventory = p.getOpenInventory().getTopInventory();
        Inventory bag = p.getInventory();

        Collection<ItemStack> stacks = bag.addItem(ArraysUtils.removeNullFromArray(inventory.getContents())).values();
        inventory.clear();

        if(!stacks.isEmpty())
            inventory.addItem(stacks.toArray(new ItemStack[0]));
    }

    private void close(Player p) {

        if(Holder.isInstanceof(p.getOpenInventory().getTopInventory(), SkeletonTrade.class)) {

            FairTrade.getFt().taskAtNextTick(p::closeInventory);
        }
    }
}
