package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.utils.ItemStaxs;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSlotXP extends AbstractSlotLocked {

    @Override
    public ItemStack getDefault() {

        return ItemStaxs.rename(new ItemStack(Material.EXPERIENCE_BOTTLE), EnumI18n.XP_AMOUNT.localize(0));
    }

    protected void updateText(Inventory inv, int xp) {

        // "%," for inserting coma each 3-digits group

        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotLessXP.class)), EnumI18n.XP_AMOUNT.localize(String.format("%,f", xp)));
        ItemStaxs.rename(inv.getItem(getSkeleton().firstSlot(SlotMoreXP.class)), EnumI18n.XP_AMOUNT.localize(String.format("%,f", xp)));
    }
}