package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.Trade;
import fr.rafoudiablol.ft.utils.ItemStacksUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SlotConfirm extends AbstractSlotTrade {

    @Override
    public ItemStack getDefault() {

        ItemStack ret = new ItemStack(Material.WRITTEN_BOOK);
        ItemStacksUtils.rename(ret, EnumI18n.CONFIRM.localize());
        return ret;
    }

    @Override
    public boolean action(InventoryClickEvent e, Trade t, Offer o) {

        if(e.getAction() == InventoryAction.PICKUP_ALL) {

            o.toggle();

            StatusTransactionEvent event = new StatusTransactionEvent(t, o);
            Bukkit.getPluginManager().callEvent(event);
        }

        return false;
    }
}
