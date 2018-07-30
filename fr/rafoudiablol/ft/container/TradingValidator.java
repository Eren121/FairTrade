package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.container.TradeMarker;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TradingValidator {

    public boolean isValid(InventoryEvent e)
    {
        InventoryHolder h = e.getInventory().getHolder();
        return h instanceof TradeMarker && ((TradeMarker)h).running;
    }

    public void invalidate(Player p)
    {
        if(p.getOpenInventory() != null)
            invalidate(p.getOpenInventory().getTopInventory());
    }

    public void invalidate(Inventory inv)
    {
        InventoryHolder h = inv.getHolder();
        if(h instanceof TradeMarker) {
            ((TradeMarker)h).running = false;
        }
    }
}
