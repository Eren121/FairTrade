package fr.rafoudiablol.ft.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDecoratorPlayerArg implements CommandExecutor {

    private final CommandExecutor exe;

    public CommandDecoratorPlayerArg(CommandExecutor cmd)
    {
        exe = cmd;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1)
        {
            Player player = Bukkit.getPlayer(strings[0]);

            if(player != null)
            {
                return exe.onCommand(commandSender, command, s, strings);
            }
            else
            {
                commandSender.sendMessage(String.format("/%s: player %s not found", s, strings[0]));
            }
        }
        else
        {
            commandSender.sendMessage(command.getUsage());
        }

        return false;
    }
}
