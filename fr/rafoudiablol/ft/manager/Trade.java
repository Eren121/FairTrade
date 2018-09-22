package fr.rafoudiablol.ft.manager;

public class Trade {

    protected Offer offers[] = new Offer[2];

    public Trade() {

    }

    public void setOffer(int i, Offer offer) {
        offers[i] = offer;
    }

    public Offer getOffer(int i) {
        return offers[i];
    }
}
