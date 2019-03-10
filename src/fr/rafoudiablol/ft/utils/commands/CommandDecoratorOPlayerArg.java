package fr.rafoudiablol.ft.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command for one player-argument different than the command source
 */
public class CommandDecoratorOPlayerArg implements CommandExecutor {

    private final CommandExecutor exe;

    public CommandDecoratorOPlayerArg(CommandExecutor cmd)
    {
        exe = cmd;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {

        if(params.length == 1)
        {
            Player player = Bukkit.getPlayer(params[0]);

            if(player != null)
            {
                if(player != commandSender) {
                    return exe.onCommand(commandSender, command, s, params);
                }
                else {
                    commandSender.sendMessage(String.format("/%s: You cannot target yourself.", params[0]));
                }
            }
            else
            {
                commandSender.sendMessage(String.format("/%s: Player %s not found.", s, params[0]));
            }
        }
        else
        {
            commandSender.sendMessage(command.getUsage());
        }

        return false;
    }
}
