package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.UpdateTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.AbstractSlot;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

//TODO: confirm is not reseted to false when changed now!

public class SlotOwner extends AbstractSlot {

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot) {
        
        getFt().taskAtNextTick(() -> updateInventory(human, inv, slot));
        return true;
    }

    private void updateInventory(HumanEntity human, Inventory inv, int slot)
    {
        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());

        if(status != null) {
            UpdateTransactionEvent event = new UpdateTransactionEvent(status.getPlayer(), status.getOther(), inv, slot);
            Bukkit.getPluginManager().callEvent(event);
            updateInventory(event);
        }
        else {
            getFt().w("Orphan player trading '" + human.getName() + "'");
        }
    }

    private void updateInventory(UpdateTransactionEvent e) {

        AbstractSkeleton sk = Holder.tryGet(e.getInventory().getHolder());
        int index = sk.nth(e.getSlot());
        int slot = sk.nth(SlotRemote.class, index);

        e.getOther().getOpenInventory().setItem(slot, e.getInventory().getItem(e.getSlot()));
    }
}
