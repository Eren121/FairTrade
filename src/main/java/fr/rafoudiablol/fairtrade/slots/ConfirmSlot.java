package fr.rafoudiablol.fairtrade.slots;

import fr.rafoudiablol.fairtrade.events.AcceptedTransactionEvent;
import fr.rafoudiablol.fairtrade.events.ToggleConfirmation;
import fr.rafoudiablol.fairtrade.screens.transaction.SkinnedSlot;
import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.Protagonist;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.plugin.Inventories;
import fr.rafoudiablol.plugin.ItemStacks;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ConfirmSlot extends SkinnedSlot {

    public ConfirmSlot(TransactionScreen screen, int rawSlot) {
        super(screen, rawSlot);
    }

    @Override
    protected void onEvent(InventoryClickEvent e, @NotNull Transaction transaction, @NotNull Offer offer) {
        if (e.getAction() == InventoryAction.PICKUP_ALL) {
            if(!offer.hasConfirmed()) {
                if(!Inventories.enoughPlaceFor(offer.getPlayer().getInventory(), offer.getReceivingItems(screen))) {
                    final ItemStack item = getSkin(transaction, offer.getProtagonist());
                    ItemStacks.addLore(item, plugin.messages.notEnoughPlace.translate());
                    offer.getPlayer().getOpenInventory().getTopInventory().setItem(rawSlot, item);
                    return;
                }
            }

            offer.setConfirmed(!offer.hasConfirmed());

            if(transaction.isReady()) {

                final AcceptedTransactionEvent event = new AcceptedTransactionEvent(transaction);
                Bukkit.getPluginManager().callEvent(event);

                if(event.isCancelled()) {

                    offer.setConfirmed(false);

                    final ItemStack item = getSkin(transaction, offer.getProtagonist());
                    ItemStacks.addLore(item, event.getCancelReason());
                    offer.getPlayer().getOpenInventory().getTopInventory().setItem(rawSlot, item);
                }
            }
            else {

                final ToggleConfirmation event = new ToggleConfirmation(transaction);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }

    @Override
    public ItemStack getSkin(@NotNull Transaction transaction, @NotNull Protagonist protagonist) {
        final String lore = transaction.getOffer(protagonist).hasConfirmed() ?
            plugin.messages.buttons_confirm_true.translate() :
            plugin.messages.buttons_confirm_false.translate();

        return ItemStacks.addLore(super.getSkin(transaction, protagonist), lore);
    }

    @Override
    public String getSkinName() {
        return "confirm";
    }
}
