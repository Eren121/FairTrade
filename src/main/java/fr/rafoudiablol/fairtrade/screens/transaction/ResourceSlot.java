package fr.rafoudiablol.fairtrade.screens.transaction;

import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.TradeResource;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.events.ChangeOffer;
import fr.rafoudiablol.fairtrade.transaction.Protagonist;
import fr.rafoudiablol.plugin.ItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResourceSlot extends SkinnedSlot {
    static class ActionAmount {
        private final boolean negative;
        private final boolean more;

        ActionAmount(boolean negative, boolean more) {
            this.negative = negative;
            this.more = more;
        }

        public double getAmount(TradeResource resource) {
            double amount = more ? resource.getBigStep() : resource.getStep();
            return negative ? -amount : amount;
        }
    }

    private @NotNull TradeResource resource;

    public ResourceSlot(TransactionScreen screen, int rawSlot, @NotNull TradeResource resource) {
        super(screen, rawSlot);
        this.resource = resource;
        screen.addObserver(Event.UPDATE_OFFER, this);
    }

    /**
     * Left click -> add, right  click -> remove, shift to set more.
     * Hotbars are considered left clicks. Do not fix it because it can be useful and more reposing than clicking.
     * Also, to have an equivalent key for less, drop (action) will remove and drop all (action) will remove more.
     */
    public @Nullable ActionAmount getAction(InventoryClickEvent e) {
        System.out.println(e.getAction() + "/" + e.isShiftClick());

        if(e.getAction() == InventoryAction.DROP_ONE_SLOT) {
            return new ActionAmount(true, false);
        }
        else if(e.getAction() == InventoryAction.DROP_ALL_SLOT) {
            return new ActionAmount(true, true);
        }
        else {
            return new ActionAmount(e.isRightClick(), e.isShiftClick());
        }
    }

    @Override
    protected void onEvent(InventoryClickEvent e, @NotNull Transaction transaction, @NotNull Offer offer) {
        final ActionAmount action = getAction(e);
        final Player player = offer.getPlayer();

        if(action != null) {
            final double previousQuantity = offer.getResources(resource.getName());
            final double amount = resource.clamp(previousQuantity + action.getAmount(resource), player);

            if(amount != previousQuantity) {
                offer.setResources(resource.getName(), amount);
                final ChangeOffer event = new ChangeOffer(transaction, ChangeOffer.Type.RESOURCE);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }
    @Override
    public void onEvent(Event event, @NotNull Transaction transaction) {
        super.onEvent(event, transaction);

        if(event == Event.UPDATE_OFFER) {
            updateSlotWithSkin(transaction);
        }
    }

    @Override
    public ItemStack getSkin(@NotNull Transaction transaction, @NotNull Protagonist protagonist) {
        final double giveResource = transaction.getOffer(protagonist).getResources(resource.getName());
        final double receivingResource = transaction.getOffer(protagonist.opposite()).getResources(resource.getName());
        final double difference = receivingResource - giveResource;
        final String lore = plugin.messages.buttons_resource.translate(
            resource.formatResource(giveResource, false),
            resource.formatResource(receivingResource, false),
            getDifferenceColor(difference) + resource.formatResource(difference, true)
        );

        return ItemStacks.addLore(super.getSkin(transaction, protagonist), lore);
    }

    private String getDifferenceColor(double difference) {
        if(difference > 0) {
            return "§a";
        }
        else if(difference < 0) {
            return "§c";
        }
        else {
            return "";
        }
    }
}
