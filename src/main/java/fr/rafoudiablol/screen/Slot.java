package fr.rafoudiablol.screen;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Slot {
    protected final Screen screen;
    protected final int rawSlot;

    public Slot(Screen screen, int rawSlot) {
        this.screen = screen;
        this.rawSlot = rawSlot;
    }

    /**
     * @return The item is when the GUI is just opened by a player and initialized.
     * All slots have to be initialized (this not belongs only to any special container), by default they are empty
     * and filled with Material.AIR.
     */
    public @NotNull ItemStack getDefaultItem() {
        return new ItemStack(Material.AIR);
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isCloneable() {
        return true;
    }

    /**
     * Called on each event, independently if the even was accepted or not. This method can't change the result
     * of the event (accept input or not), this one is determinated by if the slot is writable or cloneable,
     * this method is more used for button gui for unwritable slots.
     */
    public void onEvent(InventoryClickEvent e, InventoryAction action, HumanEntity whoClicked) { }

    public void onEvent(InventoryDragEvent e, HumanEntity whoClicked) {
    }

    public int getRawSlot() {
        return rawSlot;
    }
}