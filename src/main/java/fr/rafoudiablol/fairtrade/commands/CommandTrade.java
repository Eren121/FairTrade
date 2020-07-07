package fr.rafoudiablol.fairtrade.commands;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.events.AskForTransaction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrade extends PluginCommand {

    public CommandTrade(FairTrade plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.messages.playersOnly.translate());
            return true;
        }

        final Player initiator = (Player)sender;

        if(args.length != 1) {
            return false;
        }

        final String replierName = args[0];
        final Player replier = Bukkit.getPlayer(replierName);

        if(replier == null) {
            sender.sendMessage(plugin.messages.playerNotFound.translate(replierName));
            return true;
        }

        final AskForTransaction event = new AskForTransaction(initiator, replier);
        event.setCancellationReason(plugin.messages.genericReason.translate(replier));
        Bukkit.getPluginManager().callEvent(event);

        if(event.isCancelled()) {
            initiator.sendMessage(event.getCancellationReason());
            return true;
        }

        /*
        plugin.waitingScreen.openGUI(initiator);

        initiator.getOpenInventory().getTopInventory().addItem(item);

        initiator.getOpenInventory().getTopInventory().addItem(new ItemStack(Material.OAK_PLANKS));
        */

        plugin.waitingScreen.openGUI(initiator, replier);
        return true;
    }
}
