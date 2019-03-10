package fr.rafoudiablol.ft.trade;

import org.bukkit.entity.Player;

public class OfflineOffer extends Offer {

    protected String name;

    public OfflineOffer() {
        super(null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getPlayer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getConfirm() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void toggle() {
        throw new UnsupportedOperationException();
    }

    public void setName(String playerName) {
        this.name = playerName;
    }
}
