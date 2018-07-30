package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.events.ToggleTransactionEvent;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class TaskConfirm extends TaskDeny {

    @Override
    public void run(InventoryClickEvent e, HumanEntity human, Inventory inv, int slot) {

        super.run(e, human, inv, slot);

        if(e.getAction() == InventoryAction.PICKUP_ALL) {

            PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());

            if(status != null) {
                ToggleTransactionEvent event = new ToggleTransactionEvent(status.getPlayer(), status.getOther(), inv, !status.hasConfirm());
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
}
