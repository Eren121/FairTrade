package fr.rafoudiablol.fairtrade.guards;

import fr.rafoudiablol.fairtrade.events.AcceptedTransactionEvent;
import fr.rafoudiablol.fairtrade.transaction.Transaction;
import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.events.AskForTransaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public abstract class Guard implements Listener {
    protected final FairTrade plugin;

    protected abstract Optional<String> getReason(Player first, Player second);

    public Guard(FairTrade plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onAsk(AskForTransaction e) {
        final Optional<String> opt = getReason(e.initiator, e.replier);
        if(opt.isPresent()) {
            e.setCancelled(true);
            e.setCancellationReason(opt.get());
        }
    }

    @EventHandler
    public void onAccepted(AcceptedTransactionEvent e) {
        final Transaction transaction = e.getTransaction();
        final Optional<String> opt = getReason(transaction.getInitiator().getPlayer(),
                                               transaction.getReplier().getPlayer());

        if(opt.isPresent()) {
            e.setCancelled(true);
            e.setCancelReason(opt.get());
        }
    }
}
