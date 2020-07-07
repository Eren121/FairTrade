package fr.rafoudiablol.fairtrade.ignore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandList extends FairTradeIgnore.BaseCommand {
    public CommandList(FairTradeIgnore plugin) {
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
        if(args.length == 1) {
            if(sender instanceof Player) {
                onCommand(sender, (Player)sender);
            }
            else {
                sender.sendMessage(plugin.getParent().messages.playersOnly.translate());
            }

            return true;
        }
        else if(args.length == 2) {
            final Player player = Bukkit.getPlayer(args[1]);

            if(player == null) {
                sender.sendMessage(plugin.getParent().messages.playerNotFound.translate(args[1]));
            }
            else {
                if((sender instanceof Player && sender.equals(player)) || sender.hasPermission("fairtrade.ignore.listany")) {
                    onCommand(sender, player);
                }
                else {
                    sender.sendMessage(plugin.getMissingPermissionMessage());
                }
            }

            return true;
        }

        return false;
    }

    public void onCommand(CommandSender sender, Player player) {
        final IgnoreList list = plugin.getIgnoreListOf(player);
        final List<String> connected = new ArrayList<>();

        for(UUID uuid : list) {
            final Player ignored = Bukkit.getPlayer(uuid);
            if(ignored != null) {
                connected.add(ignored.getDisplayName());
            }
        }

        sender.sendMessage(plugin.getMessages().listPlayers.translate(list.size()));
        sender.sendMessage(String.join(", ", connected));
    }
}
