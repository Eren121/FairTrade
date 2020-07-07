package fr.rafoudiablol.fairtrade.transaction;

import org.bukkit.entity.Player;

public abstract class TradeResource {
    public abstract String getName();

    public abstract String formatResource(double quantity, boolean difference);
    public abstract double clamp(double quantity, Player player);

    public double getStep() {
        return 5;
    }

    public double getBigStep() {
        return 100;
    }

    public abstract void give(Player player, double quantity);


    public double getDifference(Transaction transaction, Offer offer) {
        final double receiving = transaction.getOffer(offer.getProtagonist().opposite()).getResources(getName());
        final double giving = offer.getResources(getName());
        return receiving - giving;
    }
}
