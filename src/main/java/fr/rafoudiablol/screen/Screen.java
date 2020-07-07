package fr.rafoudiablol.screen;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Screen implements Iterable<Slot> {
    public static final int NB_SLOTS_PER_LINE = 9;
    public static final int NB_MAX_LINES = 6;
    private final ScreenListener listener;
    protected Slot[] slots;
    private final List<Slot> slotsAsList;
    private final Plugin plugin;

    public Plugin getPlugin() {
        return plugin;
    }

    public Slot getSlot(int rawSlot) {
        return slots[rawSlot];
    }

    public void setSlot(int rawSlot, Slot slot) {
        this.slots[rawSlot] = slot;
    }

    public int length() {
        return slots.length;
    }

    /**
     * Add check rawSlot >= 0 because some invalid or empty actions will set slot to -999.
     */
    public boolean doesRawSlotBelongsToCustomInventory(int rawSlot) {
        return rawSlot >= 0 && rawSlot < slots.length;
    }

    public Screen(int nbLines, Plugin plugin) {

        if(nbLines < 1 || nbLines > NB_MAX_LINES) {
            throw new IllegalArgumentException("Can't create GUI with " + nbLines + " lines, should be >= 1 and <= " + NB_MAX_LINES);
        }

        listener = new ScreenListener(this);
        slots = new Slot[nbLines * NB_SLOTS_PER_LINE];
        slotsAsList = Arrays.asList(slots);
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(listener, plugin);

        // Initialize all slots by default with an empty one
        Arrays.setAll(slots, i -> new Slot(this, i));
    }

    public void openGUI(Player player, String title) {
        final Inventory inventory = Bukkit.createInventory(null, slots.length, title);
        player.openInventory(inventory);
        listener.onOpen(player);

        for(Slot slot : slots) {
            inventory.setItem(slot.getRawSlot(), slot.getDefaultItem());
        }
    }

    public void onClosed(Player player) {
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<Slot> iterator() {
        return slotsAsList.iterator();
    }
}
