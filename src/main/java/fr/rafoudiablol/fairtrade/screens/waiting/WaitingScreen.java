package fr.rafoudiablol.fairtrade.screens.waiting;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.plugin.Variant;
import fr.rafoudiablol.fairtrade.events.TransactionInitiated;
import fr.rafoudiablol.plugin.ItemStacks;
import fr.rafoudiablol.screen.Screen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WaitingScreen extends Screen implements Listener {
    private final Map<Player, Player> waitingPlayers = new HashMap<>();
    private final FairTrade plugin;

    public WaitingScreen(FairTrade plugin) {
        super(1, plugin);
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::onUpdate, 20L, 20L);
    }

    private void onUpdate() {
        waitingPlayers.forEach((player, waited) -> {
            final Inventory inventory = player.getOpenInventory().getTopInventory();
            inventory.addItem(ItemStacks.rename(new ItemStack(Material.DIAMOND), plugin.messages.waitItem.translate(waited)));
        });
    }

    @Override
    public void onClosed(Player player) {
        super.onClosed(player);
        waitingPlayers.remove(player);
    }

    @Override
    public void openGUI(Player player, String title) {
        throw new UnsupportedOperationException("You must call openGUI(Player initiator, Player target)");
    }

    public void openGUI(Player initiator, Player target) {
        final Player waiting = waitingPlayers.get(target);
        final boolean selfTrading = initiator.equals(target);

        if(selfTrading || (waiting != null && waiting.getUniqueId().equals(initiator.getUniqueId()))) {
            waitingPlayers.remove(initiator);
            waitingPlayers.remove(target);

            Variant.forEachDifferent(initiator, target, (first, second) -> {
                plugin.transactionScreen.openGUI(first, plugin.messages.tradeTitle.translate(second));
            });

            final TransactionInitiated e = new TransactionInitiated(new Transaction(initiator, target));
            Bukkit.getPluginManager().callEvent(e);
        }
        else {
            super.openGUI(initiator, plugin.messages.waitTitle.translate(target));
            waitingPlayers.put(initiator, target);
        }
    }
}
