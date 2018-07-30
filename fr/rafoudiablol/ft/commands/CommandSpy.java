package fr.rafoudiablol.ft.commands;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.spy.ContainerSpyFactory;
import fr.rafoudiablol.ft.spy.Transaction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] params) {

        Player player = (Player)commandSender;
        Transaction tr = FairTrade.getFt().getDatabase().getTransactionFromID(Integer.valueOf(params[0]));

        if(tr != null) {
            player.openInventory(ContainerSpyFactory.getInstance().createSpy(tr));
        }
        else {
            FairTrade.getFt().sendMessage(EnumI18n.NO_TRANSACTION.localize(params[0]), commandSender);
        }

        return tr != null;
    }
}
