package fr.rafoudiablol.ft.trade;

import org.apache.commons.lang.Validate;

public class OfflineTrade extends Trade {

    protected String date;

    public OfflineTrade() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setOffer(int i, Offer offer) {
        Validate.isTrue(offer instanceof OfflineOffer);
        offers[i] = offer;
    }

    @Override
    public OfflineOffer getOffer(int i) {
        return (OfflineOffer)offers[i];
    }
}
