package fr.rafoudiablol.fairtrade.screens.transaction;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.events.ChangeOffer;
import fr.rafoudiablol.fairtrade.events.ToggleConfirmation;
import fr.rafoudiablol.fairtrade.events.TransactionInitiated;
import fr.rafoudiablol.fairtrade.events.TransactionScreenClosed;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.screen.Screen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionScreen extends Screen implements Listener {
    protected final Map<Event, List<TransactionSlot>> observers = new HashMap<>();
    protected final MirrorSlotsCache cache = new MirrorSlotsCache();
    protected final FairTrade plugin;


    @Override
    public FairTrade getPlugin() {
        return plugin;
    }

    public TransactionScreen(int nbLines, FairTrade plugin) {
        super(nbLines, plugin);
        this.plugin = plugin;

        /*
        slots[0] = new ItemSlot(this, 0, false, 0);
        slots[1] = new ItemSlot(this, 1, true, 0);
        */

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public MirrorSlotsCache getCache() {
        return cache;
    }

    @Override
    public void onClosed(Player player) {
        final Transaction transaction = plugin.transactionManager.getTransaction(player);

        if(transaction == null) {
            plugin.getLogger().warning("A player who is not registered as trading emitted a close trading GUI event (player " + player.getName() + ")");
            return;
        }

        final TransactionScreenClosed event = new TransactionScreenClosed(transaction, player);
        Bukkit.getPluginManager().callEvent(event);
    }

    @EventHandler
    public void onToggle(ToggleConfirmation event) {
        final Transaction transaction = event.getTransaction();
        fireEvent(Event.TOGGLE_CONFIRMATION, transaction);
    }

    @EventHandler
    public void onInitiated(TransactionInitiated e) {
        final Transaction transaction = e.getTransaction();
        fireEvent(Event.OPEN_GUI, transaction);
    }

    @EventHandler
    public void onUpdate(ChangeOffer e) {
        final Transaction transaction = e.getTransaction();
        fireEvent(Event.UPDATE_OFFER, transaction);
    }

    public void addObserver(Event eventType, TransactionSlot slot) {
        observers.computeIfAbsent(eventType, e -> new ArrayList<>()).add(slot);
    }

    protected void fireEvent(Event eventType, Transaction transaction) {
        if(transaction.isAborted()) {
            return;
        }

        final List<TransactionSlot> slots = observers.get(eventType);
        if(slots != null) {
            slots.forEach(slot -> slot.onEvent(eventType, transaction));
        }
    }
}
