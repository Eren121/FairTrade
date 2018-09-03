package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Listener used to update unmodifiable items of the trade inventory
 * And confirm button text
 */
public class DummyUpdater implements OnTransactionToggle {

    @EventHandler
    @Override
    public void onTransactionToggle(StatusTransactionEvent e) {

        e.forEach(p -> updateInventory(p, e));
    }

    private void updateInventory(Player p, StatusTransactionEvent e)
    {
        Inventory inv = p.getOpenInventory().getTopInventory();
        Locations loc = (p == e.getPlayer()) ? Locations.OwnerConfirm : Locations.RemoteConfirm;

        ItemStack decoration = FairTrade.getFt().getOptions().getDummyItem(e.hasConfirm());
        Skeleton.getSlots(loc).forEach(slot -> inv.setItem(slot, decoration));

        ItemStack confirm = inv.getItem(Skeleton.getConfirm());
        String title, msg;

        if(p == e.getPlayer())
        {
            if(e.hasConfirm())
            {
                title = EnumI18n.BUTTON_CANCEL.localize();
                msg = EnumI18n.YOU_ACCEPTED.localize();
            }
            else
            {
                title = EnumI18n.BUTTON_CONFIRM.localize();
                msg = EnumI18n.NOBODY_ACCEPTED.localize();
            }
        }
        else
        {
            title = EnumI18n.BUTTON_CONFIRM.localize();

            if(e.hasConfirm())
            {
                msg = EnumI18n.REMOTE_ACCEPTED.localize(e.getPlayer());
            }
            else
            {
                msg = EnumI18n.NOBODY_ACCEPTED.localize();
            }
        }

        ItemStaxs.renameAndBrief(confirm, title, msg);
    }
}
