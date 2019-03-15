package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSlotStatus extends SlotLocked {

    @Override
    public ItemStack getDefault(int i) {
        return FairTrade.getFt().getOptions().getDummyItem(false);
    }
}
