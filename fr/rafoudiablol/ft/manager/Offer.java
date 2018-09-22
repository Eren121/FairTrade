package fr.rafoudiablol.ft.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Offer {

    protected Player p; // The player that offers what following
    protected boolean confirmed; // If the player has confirmed or not
    protected ItemStack[] items; // Items that the player want to give

    public Offer(Player p) {

        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }

    public boolean getConfirm() {
        return confirmed;
    }

    public void toggle() {
        confirmed = !confirmed;
    }
}
