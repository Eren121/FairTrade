package fr.rafoudiablol.ft.listeners;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.StatusTransactionEvent;
import fr.rafoudiablol.ft.inventory.SlotConfirm;
import fr.rafoudiablol.ft.inventory.SlotStatusLocal;
import fr.rafoudiablol.ft.inventory.SlotStatusRemote;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.ItemStacksUtils;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * APIListener used to update unmodifiable items of the trade inventory
 * And confirm button text
 */
public class DummyUpdater implements Listener {

    @EventHandler
    public void event(StatusTransactionEvent e) {

        Trade trade = e.getTrade();


        FairTrade.getFt().taskAtNextTick(() -> {

            for (int i = 0; i <= 1; ++i) {

                Offer o = trade.getOffer(i);
                Offer o2 = trade.getOffer(1-i);

                updateStatus(o.getPlayer(), o2.getPlayer().getName(), o.getConfirm(), o2.getConfirm(), o.getMoney(), o2.getMoney());
            }
        });
    }

    private void updateStatus(Player player, String remoteName, boolean local, boolean remote, double localMoney, double remoteMoney) {

        Inventory inv = player.getOpenInventory().getTopInventory();
        AbstractSkeleton sk = Holder.tryGet(inv.getHolder());

        sk.byType(SlotStatusLocal.class).forEach(slot -> inv.setItem(slot, FairTrade.getFt().getOptions().getDummyItem(local)));
        sk.byType(SlotStatusRemote.class).forEach(slot -> inv.setItem(slot, FairTrade.getFt().getOptions().getDummyItem(remote)));

        ItemStack confirm = inv.getItem(sk.firstSlot(SlotConfirm.class));
        String title, msg;

        if(local) {
            title = EnumI18n.BUTTON_CANCEL.localize();
            msg = EnumI18n.YOU_ACCEPTED.localize();
        }
        else if(remote) {
            title = EnumI18n.BUTTON_CONFIRM.localize();
            msg = EnumI18n.REMOTE_ACCEPTED.localize(remoteName);
        }
        else {
            title = EnumI18n.BUTTON_CONFIRM.localize();
            msg = EnumI18n.NOBODY_ACCEPTED.localize();
        }

        if(FairTrade.getFt().getEconomy() == null) {

            ItemStacksUtils.renameAndBrief(confirm, title, msg);
        }
        else {

            ItemStacksUtils.renameAndBrief(confirm, title, msg,
                    EnumI18n.MONEY_GIVE.localize(FairTrade.getFt().formatMoney(localMoney)),
                    EnumI18n.MONEY_GET.localize(FairTrade.getFt().formatMoney(remoteMoney)));
        }
    }
}
