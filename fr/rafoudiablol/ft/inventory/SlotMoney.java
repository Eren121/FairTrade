package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.ItemStacksUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class SlotMoney extends AbstractSlotTrade {

    @Override
    public ItemStack getDefault() {
        return new ItemStack(Material.GOLD_BLOCK);
    }

    @Override
    public boolean action(InventoryClickEvent e, Trade t, Offer o) {


        double money = o.getMoney();
        money += getDelta();
        money = Math.max(money, 0);
        money = Math.min(money, FairTrade.getFt().getEconomy().getBalance(o.getPlayer()));

        o.setMoney(money);

        t.reset();
        Bukkit.getPluginManager().callEvent(new StatusTransactionEvent(t, o));

        return false;
    }

    public abstract double getDelta(); // Can be negative, of course... Checks are made

    public static class Plus extends SlotMoney {

        @Override
        public double getDelta() {
            return 10;
        }

        @Override
        public ItemStack getDefault() {
            return ItemStacksUtils.addLore(super.getDefault(), "+" + getDelta());
        }
    }

    public static class Minus extends SlotMoney {

        @Override
        public double getDelta() {
            return -10;
        }

        @Override
        public ItemStack getDefault() {
            return ItemStacksUtils.addLore(super.getDefault(), "" + getDelta());
        }
    }
}
