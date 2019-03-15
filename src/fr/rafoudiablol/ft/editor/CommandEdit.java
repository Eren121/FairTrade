package fr.rafoudiablol.ft.editor;

import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEdit implements CommandExecutor {

    protected AbstractSkeleton skeleton;

    public CommandEdit(AbstractSkeleton skeleton) {
        this.skeleton = skeleton;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player player = (Player)commandSender;
            player.openInventory(skeleton.buildInventory("Edit layout"));
            return true;
        }

        return false;
    }

    public String getName() {
        return "tradelayout";
    }
}
