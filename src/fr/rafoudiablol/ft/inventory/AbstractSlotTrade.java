package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.inv.ISlot;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Wrapper class for adding useful arguments to action() method
 */
public abstract class AbstractSlotTrade implements ISlot {

    @Override
    public final boolean action(InventoryClickEvent e) {

        Trade t = FairTrade.getFt().getTracker().getTrade(e.getWhoClicked().getUniqueId());

        if(t != null) {

            Offer o = t.getOffer(e.getWhoClicked() == t.getOffer(0).getPlayer() ? 0 : 1);
            return action(e, t, o);
        }

        // The trade was not tracked, default behaviour.
        return true;
    }

    public abstract boolean action(InventoryClickEvent e, Trade t, Offer o);
}
