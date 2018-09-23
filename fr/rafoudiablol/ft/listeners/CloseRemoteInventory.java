package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.event.Listener;

/**
 * Make sure when a player close a trade inventory, the other close to
 */
public class CloseRemoteInventory implements Listener {

    public void event(AbortTransactionEvent e) {

        if(Holder.isInstanceof(e.getPlayer().getOpenInventory().getTopInventory(), SkeletonTrade.class)) {

            FairTrade.getFt().taskAtNextTick(e.getPlayer()::closeInventory);
        }
        if(Holder.isInstanceof(e.getPlayer().getOpenInventory().getTopInventory(), SkeletonTrade.class)) {

            FairTrade.getFt().taskAtNextTick(e.getOther()::closeInventory);
        }
    }
}
