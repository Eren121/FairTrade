package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.AbstractTransactionEvent;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RequiredDistance implements Listener {

    @EventHandler
    public void event(RequestTransactionEvent e) {
        doEvent(e, 0);
    }

    @EventHandler
    public void event(InitiateTransactionEvent e) {
        doEvent(e, 0, 1);
    }

    private void doEvent(AbstractTransactionEvent e, int... ids) {

        Player p1 = e.getTrade().getOffer(0).getPlayer();
        Player p2 = e.getTrade().getOffer(1).getPlayer();
        String reason = getReason(p1, p2);

        if (reason != null) {

            for(int id : ids)
                FairTrade.getFt().sendMessage(reason, e.getTrade().getOffer(id).getPlayer());

            ((Cancellable)e).setCancelled(true);
        }
    }

    private String getReason(Player p1, Player p2) {
        if (p1.getWorld() != p2.getWorld()) {
            return EnumI18n.REASON_WORLD.localize();
        } else if (p1.getLocation().distanceSquared(p2.getLocation()) > FairTrade.getFt().getOptions().getDistanceMaxSq()) {
            return EnumI18n.REASON_DISTANCE.localize();
        } else {
            return null;
        }
    }
}
