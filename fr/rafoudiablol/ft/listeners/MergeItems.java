package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.utils.InventoriesUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

/**
 * Swap items when players finalize transaction
 */
public class MergeItems implements Listener {

    public MergeItems(Logger l) {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(FinalizeTransactionEvent e) {

        e.forEach(p -> {

            InventoriesUtils.merge(SlotRemote.class, p.getInventory(), p.getOpenInventory().getTopInventory());
        });
    }
}
