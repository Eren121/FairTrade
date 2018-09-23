package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.UpdateTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.manager.Trade;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

//TODO: confirm is not reseted to false when changed now!

public class SlotOwner extends AbstractSlotTrade {

    @Override
    public boolean action(InventoryClickEvent e, Trade t, Offer o) {

        UpdateTransactionEvent event = new UpdateTransactionEvent(t, o, e.getSlot());
        Bukkit.getPluginManager().callEvent(event);

        getFt().taskAtNextTick(() -> updateRemoteInventory(event));

        return true;
    }

    private void updateRemoteInventory(UpdateTransactionEvent e) {

        AbstractSkeleton sk = Holder.tryGet(e.getInventory().getHolder());
        int index = sk.nth(e.getSlot());
        int slot = sk.nth(SlotRemote.class, index);

        e.getOther().getOpenInventory().setItem(slot, e.getInventory().getItem(e.getSlot()));
    }
}
