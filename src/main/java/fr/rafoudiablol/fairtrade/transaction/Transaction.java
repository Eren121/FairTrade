package fr.rafoudiablol.fairtrade.transaction;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Transaction implements Iterable<Offer> {
    private boolean aborted; // True when one player has closed the GUI, leave the server or is dead
    private final Offer initiator;
    private final Offer replier;
    private final List<Offer> allOffers;

    public Transaction(Player initiator, Player replier) {
        this.initiator = new Offer(Protagonist.INITIATOR, initiator);

        if(initiator.equals(replier)) {
            this.replier = this.initiator;
        } else {
            this.replier = new Offer(Protagonist.REPLIER, replier);
        }

        this.allOffers = Collections.unmodifiableList(Lists.newArrayList(this.initiator, this.replier));
    }

    public Offer getInitiator() {
        return initiator;
    }

    public Offer getReplier() {
        return replier;
    }

    public @NotNull Offer getOffer(@NotNull Protagonist protagonist) {
        if(protagonist == Protagonist.INITIATOR) {
            return initiator;
        }
        else {
            return replier;
        }
    }

    @NotNull
    @Override
    public Iterator<Offer> iterator() {
        return allOffers.iterator();
    }

    public boolean isReady() {
        return allOffers.stream().allMatch(Offer::hasConfirmed);
    }

    public boolean isAborted() {
        return aborted;
    }

    public void abort() {
        this.aborted = true;
    }
}
