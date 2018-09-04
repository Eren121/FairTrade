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

public class SlotLessXP extends AbstractSlotXP {

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public boolean action(InventoryAction action, HumanEntity human, Inventory inv, int slot, ClickType click) {

        PlayerStatus status = getFt().getManager().getStatus(human.getUniqueId());
        Player player = status.getPlayer();

        //TODO XpChangedTransactionEvent

        int xp = FairTrade.getFt().getOptions().getExpAmount();
        xp *= click.isShiftClick() ? 10 : 1;
        status.xp -= xp;
        status.xp = Math.max(0, status.xp);

        updateText(inv, status.xp);
        return false;
    }
}
