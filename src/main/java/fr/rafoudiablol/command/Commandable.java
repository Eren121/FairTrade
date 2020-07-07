package fr.rafoudiablol.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Automatically bind arguments for each method having the @Executor annotation.
 * To know which method get called, it's RANDOMLY
 * That's why for each possible set of given arguments, there must be at maximum one possible method:
 * for example, don't overload methods with int and String arguments because "10" can cast to int and String for
 * example, and the order of evaluation is random (not determined by the declaration order of the functions
 * https://stackoverflow.com/questions/1097807/java-reflection-is-the-order-of-class-fields-and-methods-standardized)
 *
 * A rule of thumb is to not have for the same commandSender type, two functions with the same number of arguments.
 * Another rule is, that if there is a CommandSender handler, do not create another functions with Player handler
 * with same arguments because the two methods are applicables for Players commandSenders and we don't know which
 * method will get called.
 * But you can overload with no problems a Player and a ConsoleCommandSender handler method executor with no problem
 * with the same arguments.
 */
public class Commandable implements CommandExecutor, TabCompleter {
    private final List<MethodExecutor> executors = new ArrayList<>();

    public Commandable() {
        MethodDiscover.discover(this, executors);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for(final MethodExecutor executor : executors) {
            final Optional<Boolean> opt = executor.command(sender, args);
            if(opt.isPresent()) {
                return opt.get();
            }
        }

        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> list = new ArrayList<>();

        for(final MethodExecutor executor : executors) {
            final Optional<String> opt = executor.tabComplete(sender, args);
            opt.ifPresent(list::add);
        }

        if(!list.isEmpty()) {
            return list;
        }
        else {
            return null;
        }
    }
}
