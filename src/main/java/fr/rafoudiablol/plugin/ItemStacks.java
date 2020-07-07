package fr.rafoudiablol.plugin;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemStacks {
    /**
     * Static class
     */
    private ItemStacks() {}

    public static @NotNull ItemStack applyCustomization(@NotNull ItemStack item, @NotNull Consumer<ItemMeta> consumer) {
        final ItemMeta meta = item.getItemMeta();
        consumer.accept(meta);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * @return same Item as the argument (useful to wrap an expression without to having to make a variable)
     *         (not a copy!)
     */
    public static @NotNull ItemStack rename(@NotNull ItemStack item, final @NotNull String displayName) {
        return applyCustomization(item, meta -> meta.setDisplayName(displayName));
    }

    public static @NotNull ItemStack invisibleName(@NotNull ItemStack item) {
        return applyCustomization(item, meta -> meta.setDisplayName("§1")); // Trick because "" is ignored
    }

    /**
     * @param lore new-line separated lines of the lore
     */
    public static @NotNull ItemStack addLore(@NotNull ItemStack item, @NotNull String lore) {
        return applyCustomization(item, meta -> {
            @NotNull final List<String> list = Variant.ifNull(meta.getLore(), ArrayList::new);
            list.addAll(Arrays.asList(lore.split("\n")));
            meta.setLore(list);
        });
    }
}
