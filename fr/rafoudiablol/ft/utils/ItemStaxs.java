package fr.rafoudiablol.ft.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class ItemStaxs {

    public static void rename(ItemStack i, String display)
    {
        setMeta(i, meta -> meta.setDisplayName(display));
    }

    public static void brief(ItemStack i, String... des)
    {
        setMeta(i, meta -> meta.setLore(asList(des)));
    }

    public static void renameAndBrief(ItemStack i, String s1, String... s2)
    {
        rename(i, s1);
        brief(i, s2);
    }

    public static void setMeta(ItemStack i, Consumer<ItemMeta> f)
    {
        ItemMeta meta = i.getItemMeta();
        f.accept(meta);
        i.setItemMeta(meta);
    }
}
