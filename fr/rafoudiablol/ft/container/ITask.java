package fr.rafoudiablol.ft.container;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Task to do with the given event when the player click on specific slot type
 */
public interface ITask {

    void run(InventoryClickEvent e, HumanEntity human, Inventory inv, int slot);
}