package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.utils.Inventoris;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class TradingListener implements Listener {

    public TradingListener(Logger l) {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(FinalizeTransactionEvent e) {

        e.setOtherGift(Inventoris.takeRemoteAndClear(e.getPlayer(), e.getPlayer().getOpenInventory().getTopInventory()));
        e.setPlayerGift(swapper.takeRemoteAndClear(e.getOther(), e.getOther().getOpenInventory().getTopInventory()));
    }
}
