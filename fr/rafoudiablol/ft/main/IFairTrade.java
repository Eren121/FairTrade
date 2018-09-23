package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.config.IOptions;
import fr.rafoudiablol.ft.manager.ITransactionManager;
import fr.rafoudiablol.ft.listeners.TradeTracker;
import fr.rafoudiablol.ft.spy.Database;
import org.bukkit.command.CommandSender;

public interface IFairTrade extends ILoggeable {

    IOptions getOptions();
    Database getDatabase();
    TradeTracker getTracker();
    ITransactionManager getManager();
    void taskAtNextTick(Runnable task);
    void sendMessage(String msg, CommandSender... players);
}
