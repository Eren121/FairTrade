package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.utils.InventoriesUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

/**
 * Swap items when players finalize transaction, also make finalize transaction item list
 */
public class MergeItems implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(FinalizeTransactionEvent e) {

        for(int i = 0; i < 1; ++i) {

            Offer offer = e.getTrade().getOffer(i);
            Player player = offer.getPlayer();

            e.getTrade().getOffer(1-i).setItems(InventoriesUtils.merge(SlotRemote.class, player.getInventory(), player.getOpenInventory().getTopInventory()));
        }
    }
}
