package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

public class SkeletonTrade extends AbstractSkeleton {

    public static SkeletonTrade instance;

    public SkeletonTrade(int[] slots) {

        instance = this;
        registerSlot(0, new SlotEmpty());
        registerSlot(1, new SlotLocal());
        registerSlot(2, new SlotRemote());
        registerSlot(3, new SlotConfirm());

        if(FairTrade.getFt().getEconomy() != null) {

            registerSlot(4, new SlotMoney.Plus());
            registerSlot(5, new SlotMoney.Minus());
        }
        else {

            registerSlot(4, new SlotEmpty());
            registerSlot(5, new SlotEmpty());
        }

        registerSlot(7, new SlotStatusLocal());
        registerSlot(8, new SlotStatusRemote());
        setMatrix(slots);
    }

    /**
     * minimalist default constructor, used if options are broken
     */
    public SkeletonTrade() {
       this(new int[] {1, 1, 1, 7, 3, 8, 2, 2, 2});
    }

    @Override
    public void close(HumanEntity src) {

        Trade trade = FairTrade.getFt().getTracker().getTrade(src.getUniqueId());

        if(trade != null) {
            Bukkit.getPluginManager().callEvent(new AbortTransactionEvent(trade));
        }
    }
}
