package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class SlotConfirm extends AbstractSlotLocked {

    @Override
    public ItemStack getDefault() {

        ItemStack ret = new ItemStack(Material.WRITTEN_BOOK);
        ItemStaxs.rename(ret, EnumI18n.CONFIRM.localize());
        return ret;
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {

        if(action == InventoryAction.PICKUP_ALL) {

            PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());

            if(status != null) {
                StatusTransactionEvent event = new StatusTransactionEvent(status.getPlayer(), status.getOther(), inv, !status.hasConfirm());
                Bukkit.getPluginManager().callEvent(event);
            }
        }

        return false;
    }
}
