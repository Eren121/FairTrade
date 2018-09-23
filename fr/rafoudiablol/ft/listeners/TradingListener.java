package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.utils.InventoriesUtils;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

/**
 * Manage player gift
 * And un-trace inventory if transaction finalized
 */
public class TradingListener implements Listener {

    public TradingListener(Logger l) {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(FinalizeTransactionEvent e) {

        e.setOtherGift(InventoriesUtils.merge(SlotRemote.class, e.getPlayer().getInventory(), e.getPlayer().getOpenInventory().getTopInventory()));
        e.setPlayerGift(InventoriesUtils.merge(SlotRemote.class, e.getOther().getInventory(), e.getOther().getOpenInventory().getTopInventory()));
    }
}
