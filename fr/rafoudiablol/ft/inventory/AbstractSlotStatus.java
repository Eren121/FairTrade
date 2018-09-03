package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotLocked;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSlotStatus extends AbstractSlotLocked {

    @Override
    public ItemStack getDefault() {
        return FairTrade.getFt().getOptions().getDummyItem(false);
    }
}
