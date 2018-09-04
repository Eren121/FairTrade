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

public class SlotMoreXP extends AbstractSlotLocked {

    public int amount = 5;

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {

        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());
        Player player = status.getPlayer();

        if(action == InventoryAction.PICKUP_HALF) amount *= 10;

        status.xp += amount;
        player.giveExp(amount);

        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotLessXP.class)), "Give " + status.xp + " Xp");
        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotMoreXP.class)), "Give " + status.xp + " Xp");
        return false;
    }

    @Override
    public ItemStack getDefault() {

        return ItemStaxs.rename(new ItemStack(Material.EXPERIENCE_BOTTLE), "Giving 0 Xp");
    }
}
