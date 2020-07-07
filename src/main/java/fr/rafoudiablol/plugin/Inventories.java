package fr.rafoudiablol.plugin;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class Inventories {
    private static final Map<Integer, Inventory> testInventories = new HashMap<>();

    private static Inventory get(int size) {
        return testInventories.computeIfAbsent(size, i -> Bukkit.createInventory(null, size));
    }

    public static List<ItemStack> cloneAll(ItemStack... items) {
        return cloneAll(Arrays.asList(items));
    }

    public static List<ItemStack> cloneAll(List<ItemStack> items) {
        return items.stream().filter(Objects::nonNull).map(ItemStack::clone).collect(Collectors.toList());
    }

    /**
     * Static class
     */
    private Inventories() {}

    /**
     *
     * @param inventory
     * @param items
     * @return true if the inventory has enough place to place all the items
     *         otherwise false
     *         The inventory is not changed.
     */
    public static boolean enoughPlaceFor(Inventory inventory, List<ItemStack> items) {
        // not getContent() but getStoreContent(), excluding for example armor slots
        final List<ItemStack> contents = cloneAll(inventory.getStorageContents());
        contents.addAll(cloneAll(items));

        final Inventory test = get(inventory.getStorageContents().length);
        final boolean canFit = test.addItem(contents.toArray(new ItemStack[0])).isEmpty();
        test.clear();

        return canFit;
    }
}
