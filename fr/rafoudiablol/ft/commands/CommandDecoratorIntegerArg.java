package fr.rafoudiablol.ft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDecoratorIntegerArg implements CommandExecutor {

    private final CommandExecutor exe;
    private boolean optional;

    public CommandDecoratorIntegerArg(CommandExecutor cmd)
    {
        exe = cmd;
    }
    public CommandDecoratorIntegerArg(CommandExecutor cmd, boolean opt) { exe = cmd; optional = opt; }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1)
        {
            try
            {
                Integer.valueOf(strings[0]);
                return exe.onCommand(commandSender, command, s, strings);
            }
            catch(NumberFormatException e)
            {
                commandSender.sendMessage(String.format("/%s: cannot parse '%s' to number", s, strings[0]));
            }
        }
        else if(strings.length == 0 && optional)
        {
            return exe.onCommand(commandSender, command, s, strings);
        }
        else
        {
            commandSender.sendMessage(command.getUsage());
        }

        return false;
    }
}