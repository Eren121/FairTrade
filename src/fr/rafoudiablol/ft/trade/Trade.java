package fr.rafoudiablol.ft.trade;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Trade {

    protected Offer offers[] = new Offer[2];

    public Trade() {

    }

    public Trade(Player p1, Player p2) {

        offers[0] = new Offer(p1);
        offers[1] = new Offer(p2);
    }

    public void setOffer(int i, Offer offer) {
        offers[i] = offer;
    }

    public Offer getOffer(int i) {
        return offers[i];
    }

    /**
     * @return true if at least one player has confirmed
     */
    public boolean reset() {

        boolean was = offers[0].confirmed || offers[1].confirmed;
        offers[0].confirmed = offers[1].confirmed = false;
        return was;
    }
}
