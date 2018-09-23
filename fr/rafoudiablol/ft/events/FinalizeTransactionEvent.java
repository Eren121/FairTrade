package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Fire when all players have confirmed and trade items
 */
public class FinalizeTransactionEvent extends AbstractTransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    private ItemStack[] playerGift = new ItemStack[0];
    private ItemStack[] otherGift = new ItemStack[0];

    public FinalizeTransactionEvent(Trade trade) {
        super(trade);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setPlayerGift(ItemStack[] itemStacks) {
        playerGift = itemStacks;
    }

    public void setOtherGift(ItemStack[] itemStacks) {
        otherGift = itemStacks;
    }

    public ItemStack[] getPlayerGift() {
        return playerGift;
    }

    public ItemStack[] getOtherGift() {
        return otherGift;
    }
}