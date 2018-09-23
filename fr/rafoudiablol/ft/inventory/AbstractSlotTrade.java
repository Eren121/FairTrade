package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.Trade;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Wrapper class for adding useful arguments to action() method
 */
public abstract class AbstractSlotTrade extends AbstractSlot {

    @Override
    public final boolean action(InventoryClickEvent e) {

        Trade t = FairTrade.getFt().getTracker().getTrade(e.getWhoClicked().getUniqueId());
        Offer o = t.getOffer(e.getWhoClicked() == t.getOffer(0).getPlayer() ? 0 : 1);

        return action(e, t, o);
    }

    public abstract boolean action(InventoryClickEvent e, Trade t, Offer o);
}
