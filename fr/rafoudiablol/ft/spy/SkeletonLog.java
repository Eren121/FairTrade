package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.inventory.SlotConfirm;
import fr.rafoudiablol.ft.inventory.SlotLocal;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.OfflineTrade;
import fr.rafoudiablol.ft.utils.ItemStacksUtils;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SkeletonLog extends AbstractSkeleton {

    public SkeletonLog() {

        registerSlot(0, new SlotLocked());

        int[] m = new int[9 * 6];
        setMatrix(m);
    }

    //TODO: matrix in history

    /**
     * Build inventory matching basic trading inventory
     *
     * @param tr
     * @return
     */
    public Inventory buildInventory(OfflineTrade tr) {

        AbstractSkeleton trade = FairTrade.getFt().getOptions().getSkeleton();
        Inventory inv = super.buildInventory("[" + tr.getOffer(0).getName() + "] >>> <<< [" + tr.getOffer(1).getName() + "]");
        LinkedList<ItemStack> whatAccGive = new LinkedList<>(Arrays.asList(tr.getOffer(1).getItems()));
        LinkedList<ItemStack> whatReqGive = new LinkedList<>(Arrays.asList(tr.getOffer(0).getItems()));

        for(int i = 0; i < size(); ++i)
        {
            if(!whatReqGive.isEmpty() && trade.get(i) instanceof SlotLocal)
            {
                inv.setItem(i, whatReqGive.removeLast());
            }
            else if(!whatAccGive.isEmpty() && trade.get(i) instanceof SlotRemote)
            {
                inv.setItem(i, whatAccGive.removeLast());
            }
        }

        inv.setItem(trade.firstSlot(SlotConfirm.class), getHistoryInfoItem(tr));

        return inv;
    }

    private ItemStack getHistoryInfoItem(OfflineTrade tr)
    {
        List<String> list = EnumI18n.LOG_BRIEF.localizeList(
                tr.getOffer(0).getName(),
                tr.getOffer(1).getName(),
                FairTrade.getFt().formatMoney(tr.getOffer(0).getMoney()),
                FairTrade.getFt().formatMoney(tr.getOffer(1).getMoney()),
                tr.getDate());

        String name = list.remove(0);
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemStacksUtils.renameAndBrief(item, name, list.toArray(new String[0]));

        return item;
    }
}
