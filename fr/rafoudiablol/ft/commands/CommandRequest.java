package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRequest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {

        Player player = (Player)commandSender;
        Player arg = Bukkit.getPlayer(params[0]);

        RequestTransactionEvent e = new RequestTransactionEvent(player, arg);
        Bukkit.getPluginManager().callEvent(e);

        return true;
    }
}