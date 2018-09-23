package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.config.IOptions;
import fr.rafoudiablol.ft.listeners.TradeTracker;
import fr.rafoudiablol.ft.spy.Database;
import fr.rafoudiablol.ft.utils.ILoggable;
import org.bukkit.command.CommandSender;

public interface IFairTrade extends ILoggable {

    IOptions getOptions();
    Database getDatabase();
    TradeTracker getTracker();
    void taskAtNextTick(Runnable task);
    void sendMessage(String msg, CommandSender... players);
}
