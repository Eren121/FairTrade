package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.commands.*;
import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.config.IOptions;
import fr.rafoudiablol.ft.config.Options;
import fr.rafoudiablol.ft.listeners.*;
import fr.rafoudiablol.ft.spy.Database;
import fr.rafoudiablol.ft.spy.Queries;
import fr.rafoudiablol.ft.utils.APIListener;
import fr.rafoudiablol.ft.utils.commands.CommandDecoratorIntegerArg;
import fr.rafoudiablol.ft.utils.commands.CommandDecoratorOPlayerArg;
import fr.rafoudiablol.ft.utils.commands.CommandDecoratorPlayer;
import fr.rafoudiablol.ft.utils.commands.CommandDecoratorPlayerArg;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FairTrade extends JavaPlugin implements IFairTrade {

    private static FairTrade INSTANCE;
    private Database db;
    private TradeTracker tradeTracker;
    private Options options;

    public static IFairTrade getFt() { return INSTANCE; }

    @Override
    public void onEnable() {

        super.onEnable();
        INSTANCE = this;

        readConfiguration();
        registerCommands();
        setupDb();
        setupTracker();
        registerListeners();
        doUnitTests();
        welcome();
    }

    @Override
    public void onDisable()
    {
        db.close();
    }

    @Override
    public IOptions getOptions() {
        return options;
    }

    @Override
    public Database getDatabase() { return db; }

    @Override
    public TradeTracker getTracker() {
        return tradeTracker;
    }

    @Override
    public void taskAtNextTick(Runnable task)
    {
        getServer().getScheduler().scheduleSyncDelayedTask(this, task, 1L);
    }

    @Override
    public void sendMessage(String msg, CommandSender... players) {
        for(CommandSender p : players) {
            p.sendMessage(EnumI18n.PREFIX.localize() + msg);
        }
    }

    private void registerListeners()
    {
        getServer().getPluginManager().registerEvents(new TradingListener(getLogger()), this);
        getServer().getPluginManager().registerEvents(new RequestTracker(), this);
        getServer().getPluginManager().registerEvents(new CloseRemoteInventory(), this);
        getServer().getPluginManager().registerEvents(tradeTracker, this);
        getServer().getPluginManager().registerEvents(db, this);
        getServer().getPluginManager().registerEvents(new RequiredDistance(), this);
        getServer().getPluginManager().registerEvents(new DummyUpdater(), this);
        getServer().getPluginManager().registerEvents(new APIListener(), this);
    }

    private void registerCommands()
    {
        getCommand(TypeCommand.REQUEST.name).setExecutor(new CommandDecoratorPlayer(
            getOptions().canSelfTrade() ?
                new CommandDecoratorPlayerArg(
                        new CommandRequest()
                ) :
                new CommandDecoratorOPlayerArg(
                        new CommandRequest()
                )
        ));

        getCommand(TypeCommand.ACCEPT.name).setExecutor(new CommandDecoratorPlayer(
            new CommandAccept()
        ));

        getCommand(TypeCommand.HISTORY.name).setExecutor(new CommandDecoratorPlayer(
            new CommandDecoratorIntegerArg(
                new CommandSpy()
            )
        ));
    }

    private void doUnitTests()
    {
        getLogger().info("Do unit tests...");
        UnitTest unitTest = new UnitTest();
        unitTest.checkSkeletonInventory();
        unitTest.checkArraysUtils();
        getLogger().info("Unit Tests ok.");
    }

    private void setupDb()
    {
        File tracker = new File(getDataFolder(), "tradeTracker.sqlite");

        if(!tracker.exists())
        {
            try {
                tracker.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        db = new Database(getLogger());
        db.connect(tracker.getPath());
        db.update(Queries.CreateTable.query);
    }

    private void readConfiguration()
    {
        File conf = getDataFolder();
        if(!conf.exists())
        {
            conf.mkdirs();
        }

        saveDefaultConfig();
        options = new Options(this);
    }

    private void setupTracker()
    {
        tradeTracker = new TradeTracker();
    }

    private void welcome() {

        i(EnumI18n.WELCOME.localize(this));
    }
}
