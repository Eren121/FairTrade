package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.container.Locations;
import fr.rafoudiablol.ft.container.TradeHistoryMarker;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static fr.rafoudiablol.ft.container.Skeleton.*;

public class ContainerSpyFactory {

    private static final ContainerSpyFactory INSTANCE = new ContainerSpyFactory();

    public static ContainerSpyFactory getInstance() {
        return INSTANCE;
    }

    private ContainerSpyFactory()
    {

    }

    public Inventory createSpy(Transaction tr)
    {
        Inventory inv = Bukkit.createInventory(new TradeHistoryMarker(), INVENTORY.length, tr.getTitle());
        LinkedList<ItemStack> whatAccGive = new LinkedList<>(Arrays.asList(tr.whatAccepterGives));
        LinkedList<ItemStack> whatReqGive = new LinkedList<>(Arrays.asList(tr.whatRequesterGives));

        for(int i = 0; i < INVENTORY.length; ++i)
        {
            if(!whatReqGive.isEmpty() && INVENTORY[i] == Locations.Left.id)
            {
                inv.setItem(i, whatReqGive.removeLast());
            }
            else if(!whatAccGive.isEmpty() && INVENTORY[i] == Locations.Right.id)
            {
                inv.setItem(i, whatAccGive.removeLast());
            }
        }

        inv.setItem(getConfirm(), getHistoryInfoItem(tr));

        return inv;
    }

    private ItemStack getHistoryInfoItem(Transaction tr)
    {
        String[] info = new String[3];
        info[0] = EnumI18n.INFOS_1.localize(tr.requesterName, tr.accepterName, tr.date);
        info[1] = EnumI18n.INFOS_2.localize(tr.requesterName, tr.accepterName, tr.date);
        info[2] = EnumI18n.INFOS_3.localize(tr.requesterName, tr.accepterName, tr.date);

        ItemStack item = new ItemStack(Material.BARRIER);
        ItemStaxs.rename(item, EnumI18n.INFOS_0.localize(tr.requesterName, tr.accepterName, tr.date));
        ItemStaxs.brief(item, info);

        return item;
    }
}
