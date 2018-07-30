package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class RequiredDistance implements OnTransactionRequest, OnTransactionInitiate {

    @EventHandler
    @Override
    public void onTransactionRequest(RequestTransactionEvent e) {

        Player p1 = e.getPlayer();
        Player p2 = e.getOther();
        String reason = getReason(p1, p2);

        if (reason != null) {
            FairTrade.getFt().sendMessage(reason, p1);
            e.setCancelled(true);
        }
    }

    @Override
    public void onInitiateTransaction(InitiateTransactionEvent e) {

        Player p1 = e.getPlayer();
        Player p2 = e.getOther();
        String reason = getReason(p1, p2);

        if (reason != null) {
            FairTrade.getFt().sendMessage(reason, p2);
            e.setCancelled(true);
        }
    }

    private String getReason(Player p1, Player p2) {
        if (p1.getWorld() != p2.getWorld()) {
            return EnumI18n.REASON_WORLD.localize();
        } else if (p1.getLocation().distanceSquared(p2.getLocation()) > FairTrade.getFt().getOptions().getDistanceMinSq()) {
            return EnumI18n.REASON_DISTANCE.localize();
        } else {
            return null;
        }
    }
}
