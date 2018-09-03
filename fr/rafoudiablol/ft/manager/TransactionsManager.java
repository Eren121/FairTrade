package fr.rafoudiablol.ft.manager;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.*;
import fr.rafoudiablol.ft.listeners.OnTransactionAbort;
import fr.rafoudiablol.ft.listeners.OnTransactionInitiate;
import fr.rafoudiablol.ft.listeners.OnTransactionRequest;
import fr.rafoudiablol.ft.listeners.OnTransactionToggle;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionsManager implements
        ITransactionManager, OnTransactionRequest, OnTransactionInitiate, OnTransactionToggle, OnTransactionAbort {

    private Map<UUID, PlayerStatus> transactions;
    private Map<UUID, UUID> targetToRequester;

    public TransactionsManager() {
        transactions = new HashMap<>();
        targetToRequester = new HashMap<>();
    }

    @Override
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInitiateTransaction(InitiateTransactionEvent e) {

        e.forEach((p1, p2) -> transactions.put(p1.getUniqueId(), new PlayerStatus(p1, p2, p1 == e.getAsker())));
    }

    @Override
    @EventHandler(priority = EventPriority.MONITOR)
    public void onTransactionToggle(StatusTransactionEvent e) {

        PlayerStatus status1 = getStatus(e.getPlayerID());
        PlayerStatus status2 = getStatus(e.getOtherID());

        if(status1 != null && status2 != null) {

            status1.setConfirm(e.hasConfirm());

            if(status1.hasConfirm() && status2.hasConfirm())
            {
                FinalizeTransactionEvent e2 = FinalizeTransactionEvent.cookEvent(status1);
                Bukkit.getPluginManager().callEvent(e2);
                remove(e);
            }
        }
    }

    public PlayerStatus getStatus(UUID uuid) {
        return transactions.get(uuid);
    }

    @Override
    @EventHandler(priority = EventPriority.MONITOR)
    public void onTransactionRequest(RequestTransactionEvent e) {

        targetToRequester.put(e.getOtherID(), e.getPlayerID());
        FairTrade.getFt().sendMessage(EnumI18n.REQUEST.localize(e.getPlayer()), e.getOther());
    }

    public Player popSource(Player dest) {

        return Bukkit.getPlayer(targetToRequester.remove(dest.getUniqueId()));
    }

    @Override
    public void onTransactionAbort(AbortTransactionEvent e) {

        remove(e);
    }

    public void remove(ITransactionLink link)
    {
        link.forEach(p -> transactions.remove(p.getUniqueId()));
    }
}