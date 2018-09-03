package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.inventory.SlotConfirm;
import fr.rafoudiablol.ft.inventory.SlotOwner;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.Holder;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;

public class SkeletonLog extends AbstractSkeleton {

    public SkeletonLog() {

        registerSlot(new SlotLocked());

        int[] m = new int[9 * 6];

        Arrays.fill(m, SlotLocked.ID);
        setMatrix(m);
    }

    //TODO: matrix in history

    /**
     * Build inventory matching basic trading inventory
     *
     * @param tr
     * @return
     */
    public Inventory buildInventory(Transaction tr) {

        AbstractSkeleton trade = FairTrade.getFt().getOptions().getSkeleton();
        Inventory inv = super.buildInventory(tr.getTitle());
        LinkedList<ItemStack> whatAccGive = new LinkedList<>(Arrays.asList(tr.whatAccepterGives));
        LinkedList<ItemStack> whatReqGive = new LinkedList<>(Arrays.asList(tr.whatRequesterGives));

        for(int i = 0; i < size(); ++i)
        {
            if(!whatReqGive.isEmpty() && trade.get(i) instanceof SlotOwner)
            {
                inv.setItem(i, whatReqGive.removeLast());
            }
            else if(!whatAccGive.isEmpty() && trade.get(i) instanceof SlotRemote)
            {
                inv.setItem(i, whatAccGive.removeLast());
            }
        }

        inv.setItem(trade.getFirst(SlotConfirm.class), getHistoryInfoItem(tr));

        return inv;
    }

    private ItemStack getHistoryInfoItem(Transaction tr)
    {
        String[] info = new String[3];
        info[0] = EnumI18n.INFO_1.localize(tr.requesterName, tr.accepterName, tr.date);
        info[1] = EnumI18n.INFO_2.localize(tr.requesterName, tr.accepterName, tr.date);
        info[2] = EnumI18n.INFO_3.localize(tr.requesterName, tr.accepterName, tr.date);

        ItemStack item = new ItemStack(Material.BARRIER);
        ItemStaxs.rename(item, EnumI18n.INFO_0.localize(tr.requesterName, tr.accepterName, tr.date));
        ItemStaxs.brief(item, info);

        return item;
    }
}
