package fr.rafoudiablol.fairtrade.slots;

import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.screens.transaction.SkinnedSlot;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.transaction.Protagonist;
import fr.rafoudiablol.plugin.ItemStacks;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EmptySlot extends SkinnedSlot {
    public EmptySlot(TransactionScreen screen, int rawSlot) {
        super(screen, rawSlot);
    }

    @Override
    public ItemStack getSkin(@NotNull Transaction transaction, @NotNull Protagonist protagonist) {
        return ItemStacks.invisibleName(super.getSkin(transaction, protagonist));
    }

    @Override
    public String getSkinName() {
        return "empty";
    }
}
