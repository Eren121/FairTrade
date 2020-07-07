package fr.rafoudiablol.fairtrade.ignore;

import com.sun.istack.internal.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MainCommand extends FairTradeIgnore.BaseCommand {
    private final CommandAdd add;
    private final CommandRemove remove;
    private final CommandList list;

    public MainCommand(FairTradeIgnore plugin) {
        super(plugin);
        add = new CommandAdd(plugin);
        remove = new CommandRemove(plugin);
        list = new CommandList(plugin);
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
        if(args.length >= 1) {
            switch(args[0]) {
                case "add": return add.onCommand(sender, command, label, args);
                case "remove": return remove.onCommand(sender, command, label, args);
                case "list": return list.onCommand(sender, command, label, args);
            }
        }

        return false;
    }
}
