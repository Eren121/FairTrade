package fr.rafoudiablol.fairtrade.ignore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandRemove extends FairTradeIgnore.BaseCommand {
    public CommandRemove(FairTradeIgnore plugin) {
        super(plugin);
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(plugin.getParent().messages.playersOnly.translate());
            return true;
        }

        if(args.length == 2) {
            final Player senderPlayer = (Player)sender;
            final String name = args[1];
            final Player player = Bukkit.getPlayer(name);

            if(player == null) {
                sender.sendMessage(plugin.getParent().messages.playerNotFound.translate(name));
            }
            else {
                if(plugin.getIgnoreListOf(senderPlayer).remove(player)) {
                    sender.sendMessage(plugin.getMessages().playerRemoved.translate(player));
                }
                else {
                    sender.sendMessage(plugin.getMessages().playerNotIgnored.translate(player));
                }
            }

            return true;
        }

        return false;
    }
}
