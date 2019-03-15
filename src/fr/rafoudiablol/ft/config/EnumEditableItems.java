package fr.rafoudiablol.ft.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public enum EnumEditableItems {

    EMPTY("empty", new ItemStack(Material.BLUE_STAINED_GLASS_PANE)),
    CONFIRM("confirm", new ItemStack(Material.WRITABLE_BOOK)),
    MONEY_PLUS("money-plus", new ItemStack(Material.GOLD_BLOCK)),
    MONEY_MINUS("money-minus", new ItemStack(Material.GOLD_BLOCK)),
    STATUS_WAITING("status-waiting", new ItemStack(Material.GREEN_STAINED_GLASS_PANE)),
    STATUS_OK("status-ok", new ItemStack(Material.RED_STAINED_GLASS_PANE));

    /*
    empty:
    confirm:
    money-plus:
    money-minus:
    status-waiting:
    status-ok:*/

    private final String key;
    private final ItemStack defaultItem;

    EnumEditableItems(String key, ItemStack itemstack) {
        this.key = key;
        this.defaultItem = itemstack;
    }

    public ItemStack getItemFromConfig(ConfigurationSection config) {
        return config.getItemStack(key, defaultItem);
    }

    public ItemStack getDefaultItem() {
        return defaultItem;
    }
}
