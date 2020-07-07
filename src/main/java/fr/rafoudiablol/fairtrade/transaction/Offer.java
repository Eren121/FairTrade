package fr.rafoudiablol.fairtrade.transaction;

import fr.rafoudiablol.fairtrade.slots.ItemSlot;
import fr.rafoudiablol.screen.Screen;
import fr.rafoudiablol.screen.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Offer {
    private final Map<String, Double> resources;
    private final Protagonist protagonist;
    private final Player player;
    private boolean confirmed;

    public Offer(Protagonist protagonist, Player player) {
        this.resources = new HashMap<>();
        this.protagonist = protagonist;
        this.player = player;
    }

    public Protagonist getProtagonist() {
        return protagonist;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<ItemStack> getReceivingItems(Screen screen) {
        final List<ItemStack> items = new ArrayList<>();
        final Inventory inventory = player.getOpenInventory().getTopInventory();

        for(Slot slot : screen) {
            if(slot instanceof ItemSlot) {
                final ItemSlot itemSlot = (ItemSlot)slot;
                if(itemSlot.getSide() == Side.REMOTE) {
                    final ItemStack item = inventory.getItem(itemSlot.getRawSlot());
                    if(item != null) {
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    public Map<String, Double> getAllResources() {
        return Collections.unmodifiableMap(resources);
    }

    public double getResources(String resource) {
        return resources.getOrDefault(resource, 0.0);
    }

    public void setResources(String resource, double quantity) {
        resources.put(resource, quantity);
    }
}
