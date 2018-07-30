package fr.rafoudiablol.ft.container;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TaskDeny implements ITask
{
    @Override
    public void run(InventoryClickEvent e, HumanEntity human, Inventory inv, int slot) {

        e.setCancelled(true);
    }
}
