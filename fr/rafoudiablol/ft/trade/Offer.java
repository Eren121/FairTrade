package fr.rafoudiablol.ft.trade;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;

public class Offer {

    protected Player p; // The player that offers what following
    protected boolean confirmed; // If the player has confirmed or not
    protected ItemStack items[]; // Items that the player want to give
    protected double money; // Money the player want to give - not freely - lul

    public Offer(Player p) {

        this.p = p;
    }

    public String getName() {
        return p.getName();
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
        return items;
    }

    public void setItems(ItemStack items[]) {
        this.items = items;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double d) {
        money = d;
    }
}
