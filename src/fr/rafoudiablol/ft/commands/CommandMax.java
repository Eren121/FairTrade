package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMax implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1) {

            FairTrade.getFt().getOptions().setDistanceMax(Integer.valueOf(strings[0]));
        }

        FairTrade.getFt().sendMessage("Current max trade distance: " + FairTrade.getFt().getOptions().getDistanceMax(), commandSender);

        return true;
    }
}
