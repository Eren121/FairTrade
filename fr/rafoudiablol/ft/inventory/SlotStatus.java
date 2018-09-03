package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.inv.SlotLocked;
import org.bukkit.inventory.ItemStack;

public class SlotStatus extends SlotLocked {

    @Override
    public ItemStack getDefault() {
        return FairTrade.getFt().getOptions().getDummyItem(false);
    }

    @Override
    public int getId() {
        return 7; //TODO 7/8
    }
}
