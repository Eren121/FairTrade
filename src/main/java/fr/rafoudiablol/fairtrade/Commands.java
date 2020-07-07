package fr.rafoudiablol.fairtrade;

import fr.rafoudiablol.fairtrade.commands.CommandTrade;
import org.bukkit.command.CommandExecutor;

import java.util.function.Function;

public enum Commands {
    TRADE(CommandTrade::new);

    public final String name;
    public final Function<FairTrade, CommandExecutor> generator;

    Commands(Function<FairTrade, CommandExecutor> generator) {
        this.name = name().toLowerCase();
        this.generator = generator;
    }
}
