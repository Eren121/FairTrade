package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.inventory.ItemStack;

public class SlotEmpty extends AbstractSlotDeny {

    @Override
    public ItemStack getDefault() {
        return FairTrade.getFt().getOptions().getEmptyItem();
    }
}
