package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.InventoriesUtils;
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

            if(t.getOffer(0).getConfirm() && t.getOffer(1).getConfirm()) {

                FinalizeTransactionEvent e2 = new FinalizeTransactionEvent(t);
                Bukkit.getPluginManager().callEvent(e2);
            }
            else {

                StatusTransactionEvent e2 = new StatusTransactionEvent(t, o);
                Bukkit.getPluginManager().callEvent(e2);
            }
        }
        else if(e.getAction() == InventoryAction.PICKUP_HALF) {

            // If SELF-TRADING, right click on confirm button is to toggle remote confirmation

            if(t.getOffer(0).getPlayer().getUniqueId().equals(t.getOffer(1).getPlayer().getUniqueId())) {

                Offer o2 = (t.getOffer(o == t.getOffer(0) ? 1 : 0));
                o2.toggle();
                StatusTransactionEvent e2 = new StatusTransactionEvent(t, o2);
                Bukkit.getPluginManager().callEvent(e2);
            }
        }

        return false;
    }
}
