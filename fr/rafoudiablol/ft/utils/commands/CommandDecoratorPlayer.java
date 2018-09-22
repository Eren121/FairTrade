package fr.rafoudiablol.ft.utils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDecoratorPlayer implements CommandExecutor {

    private final CommandExecutor exe;

    public CommandDecoratorPlayer(CommandExecutor cmd)
    {
        exe = cmd;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player)
        {
            return exe.onCommand(commandSender, command, s, strings);
        }
        else
        {
            commandSender.sendMessage(String.format("/%s: Only players can send this command.", s));
            return false;
        }
    }
}
