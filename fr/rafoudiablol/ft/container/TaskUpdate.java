package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.events.UpdateTransactionEvent;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static fr.rafoudiablol.ft.container.Skeleton.getMirrorLocation;
import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class TaskUpdate implements ITask {

    @Override
    public void run(InventoryClickEvent e, HumanEntity human, Inventory inv, int slot) {

        getFt().taskAtNextTick(() -> updateInventory(human, inv, e.getSlot()));
    }

    private void updateInventory(HumanEntity human, Inventory inv, int slot)
    {
        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());

        if(status != null) {
            UpdateTransactionEvent event = new UpdateTransactionEvent(status.getPlayer(), status.getOther(), inv, slot);
            Bukkit.getPluginManager().callEvent(event);
            updateInventory(event);
        }
        else {
            getFt().w("Orphan player trading '" + human.getName() + "'");
        }
    }

    private void updateInventory(UpdateTransactionEvent e) {

        e.getOther().getOpenInventory().setItem(getMirrorLocation(e.getSlot()), e.getInventory().getItem(e.getSlot()));
    }
}