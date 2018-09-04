package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class SlotLessXP extends AbstractSlotLocked {

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {

        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());
        Player player = status.getPlayer();

        //TODO XpChangedTransactionEvent

        int amount = 5;

        if(action == InventoryAction.PICKUP_HALF) amount *= 10;

        status.xp -= amount;
        player.giveExp(-amount);

        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotLessXP.class)), "Giving " + status.xp + " Xp");
        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotMoreXP.class)), "Giving " + status.xp + " Xp");
        return false;
    }

    @Override
    public ItemStack getDefault() {

        return ItemStaxs.rename(new ItemStack(Material.EXPERIENCE_BOTTLE), "Giving 0 Xp");
    }
}
