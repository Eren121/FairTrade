package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.RequestTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class CommandRequest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {

        Player player = (Player)commandSender;
        Player arg = Bukkit.getPlayer(params[0]);

        RequestTransactionEvent e = new RequestTransactionEvent(player, arg);
        Bukkit.getPluginManager().callEvent(e);

        if(!e.isCancelled()) {

            TextComponent component = new TextComponent(EnumI18n.PREFIX.localize() + EnumI18n.REQUEST.localize(e.getPlayer()));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept"));
            e.getOther().spigot().sendMessage(component);
        }
        return true;
    }
}