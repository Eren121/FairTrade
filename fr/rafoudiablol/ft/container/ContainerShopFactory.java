package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static fr.rafoudiablol.ft.container.Skeleton.*;

public class ContainerShopFactory
{
    private static final ContainerShopFactory INSTANCE = new ContainerShopFactory();
    private ItemStack[] contents = new ItemStack[INVENTORY.length];

    private ContainerShopFactory()
    {
        ItemStack confirm = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = confirm.getItemMeta();
        meta.setDisplayName(EnumI18n.CONFIRM.localize());
        confirm.setItemMeta(meta);

        Map<Locations, ItemStack> defaultItems = new HashMap<>();
        defaultItems.put(Locations.OwnerConfirm, FairTrade.getFt().getOptions().getDummyItem(false));
        defaultItems.put(Locations.RemoteConfirm, FairTrade.getFt().getOptions().getDummyItem(false));
        defaultItems.put(Locations.Confirm, confirm);

        for(int i = 0; i < contents.length; ++i)
        {
            contents[i] = defaultItems.get(Locations.valueOf(INVENTORY[i]));
        }
    }

    public static ContainerShopFactory getInstance() {
        return INSTANCE;
    }

    public Inventory createShop(Player left, Player right)
    {
        Inventory inv = Bukkit.createInventory(new TradeMarker(), INVENTORY.length, EnumI18n.TITLE.localize(left.getDisplayName(), right.getDisplayName()));
        inv.setContents(contents);

        return inv;
    }
}
