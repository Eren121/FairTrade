package fr.rafoudiablol.ft.spy;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class YamlBuilder
{
    private static final String KEY = "item";
    private static final YamlConfiguration tmp = new YamlConfiguration();

    public static String toString(ItemStack stacks[])
    {
        tmp.set(KEY, stacks);
        return tmp.saveToString();
    }

    @SuppressWarnings("unchecked")
    public static ItemStack[] toItems(String yaml)
    {
        ItemStack[] ret = {};

        try {
            tmp.loadFromString(yaml);
            ret = ((List<ItemStack>)tmp.get(KEY)).toArray(new ItemStack[0]);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
