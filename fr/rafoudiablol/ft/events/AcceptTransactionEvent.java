package fr.rafoudiablol.ft.events;

import fr.rafoudiablol.ft.manager.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AcceptTransactionEvent extends TransactionEvent {

    private static final HandlerList handlers = new HandlerList();
    private List<ItemStack> playerGift = new ArrayList<>();
    private List<ItemStack> otherGift = new ArrayList<>();

    /**
     *
     * @param player the asker (/request)
     * @param other the replier (/accept)
     */
    public AcceptTransactionEvent(Player player, Player other) {
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

    public static AcceptTransactionEvent cookEvent(PlayerStatus status) {

        AcceptTransactionEvent ret;

        if(status.isAsker())
            ret = new AcceptTransactionEvent(status.getPlayer(), status.getOther());
        else
            ret = new AcceptTransactionEvent(status.getOther(), status.getPlayer());

        return ret;
    }

    public void setPlayerGift(List<ItemStack> itemStacks) {
        playerGift = itemStacks;
    }

    public void setOtherGift(List<ItemStack> itemStacks) {
        otherGift = itemStacks;
    }

    public List<ItemStack> getPlayerGift() {
        return playerGift;
    }

    public List<ItemStack> getOtherGift() {
        return otherGift;
    }
}