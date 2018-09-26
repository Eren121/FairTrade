package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.config.IOptions;
import fr.rafoudiablol.ft.listeners.TradeTracker;
import fr.rafoudiablol.ft.spy.Database;
import fr.rafoudiablol.ft.utils.ILoggable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;

/**
 * A public interface for {@link FairTrade} plugin.
 * Standardize all common functions.
 */
public interface IFairTrade extends ILoggable {

    Economy getEconomy();
    IOptions getOptions();
    Database getDatabase();
    TradeTracker getTracker();
    void taskAtNextTick(Runnable task);
    void sendMessage(String msg, CommandSender... players);
}
