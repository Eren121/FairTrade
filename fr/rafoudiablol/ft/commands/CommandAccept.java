package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.InitiateTransactionEvent;
import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.Offer;
import fr.rafoudiablol.ft.manager.Trade;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player dst = (Player)commandSender;
        Player src = FairTrade.getFt().getManager().popSource(dst);

        if(src != null) {

            Trade trade = new Trade();
            trade.setOffer(0, new Offer(src));
            trade.setOffer(1, new Offer(dst));

            InitiateTransactionEvent event = new InitiateTransactionEvent(trade);
            SkeletonTrade sk = FairTrade.getFt().getOptions().getSkeleton();
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                event.forEach((p1, p2) -> p1.openInventory(sk.buildInventory(EnumI18n.TITLE.localize(p1, p2))));
            }
        }
        else {
            FairTrade.getFt().sendMessage(EnumI18n.NO_REQUEST.localize(), commandSender);
        }

        return true;
    }
}
