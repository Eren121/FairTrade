package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import org.bukkit.inventory.Inventory;

public abstract class AbstractSlotXP extends AbstractSlotLocked {

    public void updateText(Inventory inv, int xp) {

        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotLessXP.class)), EnumI18n.XP_AMOUNT.localize(xp));
        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotMoreXP.class)), EnumI18n.XP_AMOUNT.localize(xp));
    }
}
