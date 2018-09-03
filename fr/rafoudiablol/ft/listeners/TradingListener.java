package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.inventory.SlotOwner;
import fr.rafoudiablol.ft.inventory.SlotRemote;
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

        e.setOtherGift(Inventoris.takeAll(SlotRemote.class, e.getPlayer()));
        e.setPlayerGift(Inventoris.takeAll(SlotRemote.class, e.getOther()));
    }
}
