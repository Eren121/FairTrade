package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Fire when all players have confirmed and trade items
 */
public class FinalizeTransactionEvent extends AbstractTransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    private ItemStack[] playerGift = new ItemStack[0];
    private ItemStack[] otherGift = new ItemStack[0];

    /**
     *
     * @param player the asker (/request)
     * @param other the replier (/accept)
     */
    public FinalizeTransactionEvent(Player player, Player other) {
        super(player, other);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static FinalizeTransactionEvent cookEvent(PlayerStatus status) {

        FinalizeTransactionEvent ret;

        if(status.isAsker())
            ret = new FinalizeTransactionEvent(status.getPlayer(), status.getOther());
        else
            ret = new FinalizeTransactionEvent(status.getOther(), status.getPlayer());

        return ret;
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