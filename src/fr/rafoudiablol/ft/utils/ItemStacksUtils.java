package fr.rafoudiablol.ft.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class ItemStacksUtils {

    public static ItemStack rename(ItemStack i, String display) {
        return setMeta(i, meta -> meta.setDisplayName(display));
    }

    public static ItemStack brief(ItemStack i, String... des) {
        return setMeta(i, meta -> meta.setLore(asList(des)));
    }

    public static ItemStack renameAndBrief(ItemStack i, String s1, String... s2) {
        rename(i, s1);
        return brief(i, s2);
    }

    public static ItemStack setMeta(ItemStack i, Consumer<ItemMeta> f) {
        ItemMeta meta = i.getItemMeta();
        f.accept(meta);
        i.setItemMeta(meta);
        return i;
    }

    public static ItemStack addLore(ItemStack i, String... lore) {
        return setMeta(i, m -> {

            if(m.hasLore())
                m.getLore().addAll(asList(lore));
            else
                m.setLore(asList(lore));
        });
    }
}
