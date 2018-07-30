package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.container.ContainerShopFactory;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        boolean ret = false;
        Player dest = (Player)commandSender;
        Player source = FairTrade.getFt().getManager().popSource(dest);

        if(source != null) {
            InitiateTransactionEvent event = new InitiateTransactionEvent(source, dest);
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                event.forEach((p1, p2) -> p1.openInventory(ContainerShopFactory.getInstance().createShop(p1, p2)));
                ret = true;
            }
        }
        else {
            FairTrade.getFt().sendMessage(EnumI18n.NO_REQUEST.localize(), commandSender);
        }

        return ret;
    }
}
