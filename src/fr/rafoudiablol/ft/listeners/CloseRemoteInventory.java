package fr.rafoudiablol.ft.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.inventory.SlotLocal;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.ArraysUtils;
import fr.rafoudiablol.ft.utils.inv.Holder;
import net.minecraft.server.v1_13_R2.InventoryUtils;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

/**
 * Make sure when a player close a trade inventory, the other close to, and keep items into player inventory
 */
public class CloseRemoteInventory implements Listener {

    @EventHandler
    public void event(PlayerDropItemEvent e) {

        ItemStack itemstack = e.getItemDrop().getItemStack();
        Map<String, Object> map = itemstack.serialize();

        Gson gson  = new Gson();
        String json = gson.toJson(map);

        new YamlConfiguration().saveToString();
        e.getItemDrop().setItemStack((ItemStack)ConfigurationSerialization.deserializeObject(gson.fromJson(json, Map.class), ItemStack.class));
    }

    @EventHandler
    public void event(AbortTransactionEvent e) {

        e.forEach(p -> {

            clearNonLocal(p);
            
            if(keep(p)) {
                
                close(p);
            }
        });
    }

    @EventHandler
    public void event(FinalizeTransactionEvent e) {

        e.forEach(p -> {

            clearNonLocal(p);
            
            if(keep(p)) {
                
                close(p);
            }
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

    private boolean keep(Player p) {

        Inventory inventory = p.getOpenInventory().getTopInventory();
        Inventory bag = p.getInventory();

        Collection<ItemStack> stacks = bag.addItem(ArraysUtils.removeNullFromArray(inventory.getContents())).values();
        inventory.clear();

        if(!stacks.isEmpty())
            inventory.addItem(stacks.toArray(new ItemStack[0]));
        
        return stacks.isEmpty();
    }

    private void close(Player p) {

        if(Holder.isInstanceof(p.getOpenInventory().getTopInventory(), SkeletonTrade.class)) {

            FairTrade.getFt().taskAtNextTick(p::closeInventory);
        }
    }
}
