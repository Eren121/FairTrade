package fr.rafoudiablol.fairtrade;

import fr.rafoudiablol.fairtrade.events.AcceptedTransactionEvent;
import fr.rafoudiablol.fairtrade.events.TransactionInitiated;
import fr.rafoudiablol.fairtrade.events.TransactionScreenClosed;
import fr.rafoudiablol.fairtrade.transaction.Offer;
import fr.rafoudiablol.fairtrade.transaction.TradeResource;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Store all current trades
 */
public class TransactionManager implements Listener {
    private final Map<String, TradeResource> registeredResources = new HashMap<>();
    private final Map<Player, Transaction> currentlyTradingPlayers = new HashMap<>();
    private final FairTrade plugin;

    public TransactionManager(FairTrade plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    public boolean isTrading(Player player) {
        return currentlyTradingPlayers.containsKey(player);
    }

    public @Nullable Transaction getTransaction(Player player) {
        return currentlyTradingPlayers.get(player);
    }


    public @Nullable Offer getOffer(Transaction transaction, Player player) {
        if(transaction.getInitiator().getPlayer().getUniqueId().equals(player.getUniqueId())) {
            return transaction.getInitiator();
        }
        else if(transaction.getReplier().getPlayer().getUniqueId().equals(player.getUniqueId())) {
            return transaction.getReplier();
        }
        else {
            return null;
        }
    }

    @EventHandler
    public void onInitiated(TransactionInitiated e) {
        final Transaction transaction = e.getTransaction();
        currentlyTradingPlayers.put(transaction.getInitiator().getPlayer(), transaction);
        currentlyTradingPlayers.put(transaction.getReplier().getPlayer(), transaction);
    }

    @EventHandler
    public void onClose(TransactionScreenClosed e) {
        final Transaction transaction = currentlyTradingPlayers.remove(e.getPlayer());
        if(transaction != null) {
            transaction.abort();
        }
    }

    /**
     * Finalize the transaction: Swap items and close GUIs
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAccepted(AcceptedTransactionEvent e) {
        final Transaction transaction = e.getTransaction();

        for(Offer offer : transaction) {
            final ItemStack[] receiving = offer.getReceivingItems(plugin.transactionScreen).toArray(new ItemStack[0]);
            final Collection<ItemStack> cannotFit = offer.getPlayer().getInventory().addItem(receiving).values();
            for(ItemStack item : cannotFit) {
                offer.getPlayer().getWorld().dropItemNaturally(offer.getPlayer().getLocation(), item);
            }

            offer.getPlayer().getOpenInventory().getTopInventory().clear();
            offer.getPlayer().closeInventory();
        }

        registeredResources.values().forEach(resource -> {
            for(Offer offer : transaction) {
                System.out.println(resource.getDifference(transaction, offer));
                resource.give(offer.getPlayer(), resource.getDifference(transaction, offer));
            }
        });
    }

    public @Nullable TradeResource getResource(String resource) {
        final TradeResource registeredResource = registeredResources.get(resource);
        if(registeredResource == null) {
            plugin.getLogger().warning("Unregistered resource: " + resource);
        }
        return registeredResource;
    }

    public void registerResource(TradeResource resource) {
        registeredResources.put(resource.getName(), resource);
    }
}
