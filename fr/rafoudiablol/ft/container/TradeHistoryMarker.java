package fr.rafoudiablol.ft.container;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Used to know if the inventory is a history trading inventory (via instanceof)
 */
public class TradeHistoryMarker implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}
