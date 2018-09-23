package fr.rafoudiablol.ft.trade;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class Offer {

    protected Player p; // The player that offers what following
    protected boolean confirmed; // If the player has confirmed or not
    protected HashMap<Integer, ItemStack> items = new HashMap<>(); // Items that the player want to give

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

    public ItemStack[] getItems() {
        return items.values().toArray(new ItemStack[0]);
    }

    public void setItem(int i, ItemStack item) {

        if(item == null)
            items.remove(i);
        else
            items.put(i, item);
    }
}
