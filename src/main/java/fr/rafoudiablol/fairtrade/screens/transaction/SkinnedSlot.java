package fr.rafoudiablol.fairtrade.screens.transaction;

import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.layout.SlotSkin;
import fr.rafoudiablol.fairtrade.transaction.Protagonist;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SkinnedSlot extends TransactionSlot {
    private SlotSkin skin;

    public SkinnedSlot(TransactionScreen screen, int rawSlot) {
        super(screen, rawSlot);
        screen.addObserver(Event.TOGGLE_CONFIRMATION, this);
        screen.addObserver(Event.OPEN_GUI, this);

        final @Nullable SlotSkin skin = plugin.layoutLoader.getSkins().getSkin(getSkinName());
        if(skin != null) {
            this.skin = skin;
        }
        else {
            this.skin = new SlotSkin();
        }
    }

    public ItemStack getSkin(@NotNull Transaction transaction, @NotNull Protagonist protagonist) {
        return skin.get(transaction.getOffer(protagonist).hasConfirmed(), this);
    }

    @Override
    public void onEvent(Event event, @NotNull Transaction transaction) {

        if(event == Event.TOGGLE_CONFIRMATION || event == Event.OPEN_GUI) {

            // Because the method getDefaultItem() has no Transaction argument,
            // we have to initialize special customization manually afterwards (custom name or especially custom lore
            // that depend on the name of the players)

            updateSlotWithSkin(transaction);
        }
    }

    public void updateSlotWithSkin(Transaction transaction) {
        for (Offer offer : transaction) {
            offer.getPlayer().getOpenInventory().getTopInventory().setItem(rawSlot, getSkin(transaction, offer.getProtagonist()));
        }
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isCloneable() {
        return false;
    }

    @Override
    public @NotNull ItemStack getDefaultItem() {
        return skin.get(false, this);
    }

    public abstract String getSkinName();
}
