package fr.rafoudiablol.ft.utils;

import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public final class APIListener implements Listener {

    @EventHandler
    public void event(InventoryClickEvent e) {

        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        AbstractSkeleton sk = Holder.tryGet(inv.getHolder());
        InventoryAction action = e.getAction();

        if(sk != null && clickedInv != null) {


            if(isActionSimple(action)) {

                if(inv == clickedInv) {

                    if(!sk.action(e)) {
                        e.setCancelled(true);
                    }
                }
            }
            else {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void event(InventoryCloseEvent e) {

        AbstractSkeleton sk = Holder.tryGet(e.getInventory().getHolder());

        if(sk != null) {
            ((Holder)e.getInventory().getHolder()).stopTracing();
            sk.close(e.getPlayer());
        }
    }

    /**
     *
     * @param e l'action
     * @return true si l'action ne concerne qu'un seul slot (par sécurité)
     */
    private boolean isActionSimple(InventoryAction e) {

        switch(e) {

            case CLONE_STACK:
            case DROP_ALL_SLOT:
            case DROP_ONE_SLOT:
            case PICKUP_ALL: //
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
            case PLACE_ALL:
            case PLACE_ONE:
            case PLACE_SOME:
            case SWAP_WITH_CURSOR:
                return true;
        }

        return false;
    }
}
