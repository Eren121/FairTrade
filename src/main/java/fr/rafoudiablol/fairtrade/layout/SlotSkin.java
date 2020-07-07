package fr.rafoudiablol.fairtrade.layout;

import fr.rafoudiablol.fairtrade.screens.transaction.SkinnedSlot;
import fr.rafoudiablol.plugin.Booleans;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Which item to show for each status.
 */
public class SlotSkin {
    protected final Map<Boolean, ItemStack> items = new HashMap<>();
    private @Nullable ItemStack defaultItem;

    public SlotSkin() {
        this(null);
    }

    /**
     * @param defaultItem The skin for all status
     */
    public SlotSkin(@Nullable ItemStack defaultItem) {
        this.defaultItem = defaultItem;
    }

    public SlotSkin(@Nullable ItemStack whenConfirmed, @Nullable ItemStack whenNotConfirmed) {
        if(whenConfirmed != null) {
            set(true, whenConfirmed);
        }
        if(whenNotConfirmed != null) {
            set(false, whenNotConfirmed);
        }
    }

    public void set(boolean status, @NotNull ItemStack item) {
        items.put(status, item);
    }

    public @NotNull ItemStack get(boolean status, SkinnedSlot slot) {
        final ItemStack item = items.get(status);
        if(item == null) {
            if(defaultItem == null) {
                return new ItemStack(Material.AIR);
            }
            return defaultItem.clone();
        }
        return item.clone();
        //return items.getOrDefault(status, defaultItem == null ? defaultItem : new Material(.AIR));
    }

    /**
     * @param serialized either an ItemStack for default value or a Map<String, ItemStack> with boolean strings as keys.
     * @return The deserialized Item or null if the type is invalid.
     */
    public static @Nullable SlotSkin deserialize(Object serialized) {
        if(serialized instanceof ItemStack) {
            return new SlotSkin((ItemStack)serialized);
        }
        else if(serialized instanceof Map) {
            final SlotSkin skin = new SlotSkin();
            final Map<?, ?> map = (Map<?, ?>)serialized;
            map.forEach((key, value) -> {
                if(key instanceof String && value instanceof ItemStack) {
                    final Boolean status = Booleans.valueOf((String)key);
                    if(status != null) {
                        skin.set(status, (ItemStack)value);
                    }
                }
            });
            return skin;
        }

        return null;
    }

    /**
     * @return either an ItemStack (if has a default value only) or a map with boolean keys and ItemStack values.
     */
    public @NotNull Object serialize() {
        if(defaultItem != null && items.isEmpty()) {
            return defaultItem;
        }

        final Map<String, Object> serialized = new HashMap<>();
        items.forEach((status, item) -> {
            serialized.put(status.toString(), item);
        });
        return serialized;
    }
}
