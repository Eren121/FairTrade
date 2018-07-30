package fr.rafoudiablol.ft.container;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Used to know if the inventory is a trading inventory (via instanceof)
 */
public class TradeMarker implements InventoryHolder {

    public boolean running = true;

    @Override
    public Inventory getInventory() {
        return null;
    }
}
