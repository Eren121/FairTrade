package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.inventory.ItemStack;

public class SlotEmpty extends SlotLocked {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public ItemStack getDefault() {
        return FairTrade.getFt().getOptions().getEmptyItem();
    }
}
