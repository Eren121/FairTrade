package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.container.*;
import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.events.AcceptTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static fr.rafoudiablol.ft.container.Skeleton.INVENTORY;

public class TradingListener implements OnInventoryClick, OnInventoryClose, OnTransactionAccept {

    private final Map<Locations, ITask> tasks;
    private final TradingValidator validator = new TradingValidator();
    private TradingSwapper swapper = new TradingSwapper();

    public TradingListener(Logger l) {
        tasks = new HashMap<>();
        tasks.put(Locations.Empty, new TaskDeny());
        tasks.put(Locations.RemoteConfirm, new TaskDeny());
        tasks.put(Locations.OwnerConfirm, new TaskDeny());
        tasks.put(Locations.Remote, new TaskDeny());
        tasks.put(Locations.Confirm, new TaskConfirm());
        tasks.put(Locations.Owner, new TaskUpdate());
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();

        if(validator.isValid(e) && clickedInv != null) {

            if(isActionAvailable(e.getAction())) {

                if(inv == clickedInv)
                    manageEvent(inv, e, e.getSlot());
            }
            else {

                e.setCancelled(true);
            }
        }
    }


    @Override
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        if(validator.isValid(e))
        {
            PlayerStatus status = FairTrade.getFt().getManager().getStatus(e.getPlayer().getUniqueId());

            // Dunno
            if(status != null) {

                Player player = status.getPlayer();
                Player other = status.getOther();

                status.forEach(p -> {validator.invalidate(p); swapper.closeInventory(p);});
                Bukkit.getPluginManager().callEvent(new AbortTransactionEvent(player, other));
            }
        }
    }

    @Override
    @EventHandler(priority = EventPriority.LOWEST)
    public void onAcceptTransaction(AcceptTransactionEvent e) {

        e.setOtherGift(swapper.takeRemoteAndClear(e.getPlayer(), e.getPlayer().getOpenInventory().getTopInventory()));
        e.setPlayerGift(swapper.takeRemoteAndClear(e.getOther(), e.getOther().getOpenInventory().getTopInventory()));
    }

    /**
     *
     * @param e l'action
     * @return true si le joueur peut effectuer l'action. Ce sont des actions simples ne concernant qu'un seul slot par sécurité
     */
    private boolean isActionAvailable(InventoryAction e)
    {
        switch(e)
        {
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

            default:
                return false;
        }
    }

    private void manageEvent(Inventory inv, InventoryClickEvent e, int slot)
    {
        ITask task = tasks.get(Locations.valueOf(INVENTORY[slot]));
        task.run(e, e.getWhoClicked(), inv, slot);
    }
}
