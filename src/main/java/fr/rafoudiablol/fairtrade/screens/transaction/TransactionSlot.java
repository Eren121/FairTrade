package fr.rafoudiablol.fairtrade.screens.transaction;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.TransactionManager;
import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.screen.Slot;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

/**
 * General-purpose slot for the transaction (unless items). This slot is not modifiable and act like a button and can
 * do an action or don't do anything (NOP). The behavior of this button can be part of the core, another plugin, or just
 * a fancy item showing information like status, tooltips or fancy barriers.
 * Most of the buttons need the information of the status of the trade to change the text and/or the icon accordingly,
 * so it's easier to put them all into a single class that define this behavior. Then, the render process can be
 * automatised to focus on the logic.
 */
public abstract class TransactionSlot extends Slot {
    protected final TransactionManager transactionManager;
    protected final TransactionScreen screen;
    protected final FairTrade plugin;

    public TransactionSlot(TransactionScreen screen, int rawSlot) {
        super(screen, rawSlot);
        this.screen = screen;
        this.plugin = screen.getPlugin();
        transactionManager = plugin.transactionManager;
    }

    /**
     * Wrapper to check if the event is well a event from a trade.
     */
    @Override
    public final void onEvent(InventoryClickEvent e, InventoryAction action, HumanEntity whoClicked) {
        if (whoClicked instanceof Player) {
            final Player player = (Player) whoClicked;
            final Transaction transaction = transactionManager.getTransaction(player);

            if(transaction == null) {
                plugin.getLogger().warning("A player who is not registered as trading emitted a trading event (player " + whoClicked.getName() + ")");
                return;
            }
            if(transaction.isAborted()) {
                return;
            }

            final Offer offer = transactionManager.getOffer(transaction, player);
            if(offer == null) {
                plugin.getLogger().warning("A player who is not registered as trading as an offer emitted a trading event (player " + whoClicked.getName() + ")");
                return;
            }

            onEvent(e, transaction, offer);
        }
    }

    @Override
    public void onEvent(InventoryDragEvent e, HumanEntity whoClicked) {
        if(whoClicked instanceof Player) {
            final Player player = (Player) whoClicked;
            final Transaction transaction = transactionManager.getTransaction(player);

            if(transaction == null) {
                plugin.getLogger().warning("A player who is not registered as trading emitted a trading event (player " + whoClicked.getName() + ")");
                return;
            }
            if(transaction.isAborted()) {
                return;
            }

            final Offer offer = transactionManager.getOffer(transaction, player);
            if(offer == null) {
                plugin.getLogger().warning("A player who is not registered as trading as an offer emitted a trading event (player " + whoClicked.getName() + ")");
                return;
            }

            onEvent(e, transaction, offer);
        }
    }

    protected void onEvent(InventoryClickEvent action, @NotNull Transaction transaction, @NotNull Offer offer) {}
    protected void onEvent(InventoryDragEvent action, @NotNull Transaction transaction, @NotNull Offer offer) {}
    public void onEvent(Event event, @NotNull Transaction transaction) {}
}
