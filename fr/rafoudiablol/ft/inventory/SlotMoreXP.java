package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class SlotMoreXP extends AbstractSlotXP {

    public int amount = 5;

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {

        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());
        Player player = status.getPlayer();

        int xp = FairTrade.getFt().getOptions().getExpAmount();
        xp *= click.isShiftClick() ? 10 : 1;
        status.xp += xp;

        updateText(inv, status.xp);
        return false;
    }

    @Override
    public ItemStack getDefault() {

        return ItemStaxs.rename(new ItemStack(Material.EXPERIENCE_BOTTLE), "Giving 0 Xp");
    }
}
