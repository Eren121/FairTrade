package fr.rafoudiablol.fairtrade.slots;

import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.screens.transaction.SkinnedSlot;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.transaction.Protagonist;
import fr.rafoudiablol.plugin.ItemStacks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Slot to fill accordingly to player's status
 * It's specially skinned because this slot has a skin, but the skins belongs to only one status (remote renders confirmed
 * when remote has confirmed, local render confirmed when local has confirmed independently of remote status,
 * also not default use of the skin parameter, that usually concerns only the local player's point of view).
 */
public class StatusSlot extends SkinnedSlot {
    public final Side side;

    public StatusSlot(TransactionScreen screen, int rawSlot, Side side) {
        super(screen, rawSlot);
        this.side = side;
    }

    @Override
    public ItemStack getSkin(@NotNull Transaction transaction, @NotNull Protagonist currentProtagonist) {
        final Protagonist protagonist = currentProtagonist.get(side);

        String lore;
        final boolean confirmed = transaction.getOffer(protagonist).hasConfirmed();

        if(side == Side.LOCAL) {
            lore = confirmed ?
                plugin.messages.buttons_status_local_true.translate() :
                plugin.messages.buttons_status_local_false.translate();
        }
        else {
            final Player player = transaction.getOffer(protagonist).getPlayer();
            lore = confirmed ?
                plugin.messages.buttons_status_remote_true.translate(player) :
                plugin.messages.buttons_status_remote_false.translate(player);
        }

        return ItemStacks.addLore(super.getSkin(transaction, protagonist), lore);
    }

    @Override
    public String getSkinName() {
        return "status";
    }
}
