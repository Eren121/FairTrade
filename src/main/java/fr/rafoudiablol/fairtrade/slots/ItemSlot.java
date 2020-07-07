package fr.rafoudiablol.fairtrade.slots;

import fr.rafoudiablol.fairtrade.events.ChangeOffer;
import fr.rafoudiablol.fairtrade.screens.transaction.Event;
import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionSlot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Slot where the player can put (or show an item) to trade or show what the other player offer.
 * Do not extends from TransactionSlot, because the item should not change when the status change.
 * TransactionSlot is used for dummy objects, status/tooltips, or others plugins. They are not modifiable, that's
 * why this one is modifiable.
 */
public class ItemSlot extends TransactionSlot {
    private final Side side;
    private final int index;
    protected final TransactionScreen screen;

    public ItemSlot(TransactionScreen screen, int rawSlot, Side side, int index) {
        super(screen, rawSlot);
        this.screen = screen;
        this.side = side;
        this.index = index;
    }

    @Override
    public boolean isWritable() {
        return side == Side.LOCAL;
    }

    public Side getSide() {
        return side;
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected void onEvent(InventoryClickEvent action, @NotNull Transaction transaction, @NotNull Offer offer) {
        if(side == Side.LOCAL) {
            onSlotChanged(transaction, offer);
        }
    }

    @Override
    protected void onEvent(InventoryDragEvent action, @NotNull Transaction transaction, @NotNull Offer offer) {
        if(side == Side.LOCAL) {
            onSlotChanged(transaction, offer);
        }
    }

    @Override
    public void onEvent(Event event, @NotNull Transaction transaction) {

    }

    protected void onSlotChanged(@NotNull Transaction transaction, @NotNull Offer offer) {
        updateRemoteItem(offer.getPlayer(), transaction.getOffer(offer.getProtagonist().opposite()).getPlayer());

        final ChangeOffer event = new ChangeOffer(transaction, ChangeOffer.Type.ITEM);
        Bukkit.getPluginManager().callEvent(event);
    }

    protected void updateRemoteItem(Player whoClicked, Player remotePlayer) {
        final ItemStack item = whoClicked.getOpenInventory().getTopInventory().getItem(rawSlot);
        final ItemSlot remoteSlot = screen.getCache().getMirror(this);
        final Inventory remoteInventory = remotePlayer.getOpenInventory().getTopInventory();

        remoteInventory.setItem(remoteSlot.rawSlot, item);
    }
}
