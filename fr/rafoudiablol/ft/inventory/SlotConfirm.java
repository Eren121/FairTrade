package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.manager.PlayerStatus;
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
    public int getId() {
        return 3;
    }

    @Override
    public boolean action(InventoryClickEvent e) {

        if(e.getAction() == InventoryAction.PICKUP_ALL) {

            PlayerStatus status = getFt().getManager().getStatus(e.getWhoClicked().getUniqueId());

            if(status != null) {
                StatusTransactionEvent event = new StatusTransactionEvent(status.getPlayer(), status.getOther(), e.getInventory(), !status.hasConfirm());
                Bukkit.getPluginManager().callEvent(event);
            }
        }

        return false;
    }
}
