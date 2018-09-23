package fr.rafoudiablol.ft.manager;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Keep an up-to-date list of current trades
 */
public class TradeTracker implements Listener {

    protected ArrayList<Trade> trades = new ArrayList<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void event(InitiateTransactionEvent e) {

        trades.add(e.getTrade());
    }

    @EventHandler
    public void event(AbortTransactionEvent e) {

        for(int i = 0; i < trades.size(); ++i) {

            if(trades.get(i).getOffer(0).getPlayer().getUniqueId().equals(e.getPlayerID())) {
                trades.remove(i);
                break;
            }
            if(trades.get(i).getOffer(1).getPlayer().getUniqueId().equals(e.getOtherID())) {
                trades.remove(i);
                break;
            }
        }
    }

    public Offer getOffer(UUID player) {

        Trade t;

        for(int i = 0; i < trades.size(); ++i) {

            t = trades.get(i);

            if(t.getOffer(0).getPlayer().getUniqueId().equals(player)) {
                return t.getOffer(0);
            }
            if(t.getOffer(1).getPlayer().getUniqueId().equals(player)) {
                return t.getOffer(1);
            }
        }

        return null;
    }

    public Trade getTrade(UUID player) {

        Trade t;

        for(int i = 0; i < trades.size(); ++i) {

            t = trades.get(i);

            if(t.getOffer(0).getPlayer().getUniqueId().equals(player)) {
                return t;
            }
            if(t.getOffer(1).getPlayer().getUniqueId().equals(player)) {
                return t;
            }
        }

        return null;
    }
}
