package fr.rafoudiablol.command;

import org.bukkit.command.CommandSender;

import java.util.Optional;

public interface MethodExecutor {
    Optional<Boolean> command(CommandSender commandSender, String[] arguments);
    Optional<String> tabComplete(CommandSender commandSender, String[] arguments);
}
