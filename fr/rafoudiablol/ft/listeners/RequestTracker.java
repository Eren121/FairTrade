package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.AcceptTransactionEvent;
import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class RequestTracker implements Listener {

    private HashMap<Player, Player> targetToRequester = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void event(RequestTransactionEvent e) {

        targetToRequester.put(e.getOther(), e.getPlayer());
        FairTrade.getFt().sendMessage(EnumI18n.REQUEST.localize(e.getPlayer()), e.getOther());
    }

    @EventHandler
    public void event(AcceptTransactionEvent e) {

        e.setTarget(targetToRequester.remove(e.getPlayer(1)));
    }
}
