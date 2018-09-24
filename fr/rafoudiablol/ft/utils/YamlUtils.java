package fr.rafoudiablol.ft.utils;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class YamlUtils
{
    private static final String KEY = "item";
    private static final YamlConfiguration tmp = new YamlConfiguration();

    public static String toString(ItemStack stacks[])
    {
        tmp.set(KEY, stacks);
        String str = (stacks != null && stacks.length == 0) ? "" : tmp.saveToString();
        str = str.substring(str.indexOf('\n')+1);
        return str;
    }

    @SuppressWarnings("unchecked")
    public static ItemStack[] toItems(String yaml)
    {
        ItemStack[] ret = {};

        if(yaml.isEmpty())
            return ret;

        try {
            tmp.loadFromString(KEY + ":\n" + yaml);
            ret = ((List<ItemStack>)tmp.get(KEY)).toArray(new ItemStack[0]);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
