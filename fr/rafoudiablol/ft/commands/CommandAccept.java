package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.AcceptTransactionEvent;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.Trade;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        AcceptTransactionEvent e = new AcceptTransactionEvent((Player)commandSender);
        Bukkit.getPluginManager().callEvent(e);

        if(e.getPlayer(0) != null) { // If a player matches

            Trade trade = new Trade();
            trade.setOffer(0, new Offer(e.getPlayer(0)));
            trade.setOffer(1, new Offer(e.getPlayer(1)));

            InitiateTransactionEvent event = new InitiateTransactionEvent(trade);
            SkeletonTrade sk = FairTrade.getFt().getOptions().getSkeleton();
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                event.forEach((p1, p2) -> p1.openInventory(sk.buildInventory(EnumI18n.TITLE.localize(p2))));
            }
        }
        else {
            FairTrade.getFt().sendMessage(EnumI18n.NO_REQUEST.localize(), commandSender);
        }

        return true;
    }
}
