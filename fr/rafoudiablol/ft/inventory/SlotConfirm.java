package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.manager.Trade;
import fr.rafoudiablol.ft.utils.ItemStacksUtils;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class SlotConfirm extends AbstractSlot {

    @Override
    public ItemStack getDefault() {

        ItemStack ret = new ItemStack(Material.WRITTEN_BOOK);
        ItemStacksUtils.rename(ret, EnumI18n.CONFIRM.localize());
        return ret;
    }

    @Override
    public boolean action(InventoryClickEvent e) {

        if(e.getAction() == InventoryAction.PICKUP_ALL) {

            Trade trade = FairTrade.getFt().getTracker().getTrade(e.getWhoClicked().getUniqueId());
            Offer offer = trade.getOffer(e.getWhoClicked() == trade.getOffer(0).getPlayer() ? 0 : 1);
            offer.toggle();

            StatusTransactionEvent event = new StatusTransactionEvent(trade, offer);
            Bukkit.getPluginManager().callEvent(event);
        }

        return false;
    }
}
