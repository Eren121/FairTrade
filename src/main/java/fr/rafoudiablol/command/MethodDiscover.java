package fr.rafoudiablol.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MethodDiscover implements MethodExecutor {
    private final CommandExecutor commandExecutor;
    private final Executor executor;
    private final Method method;
    private final List<Class<?>> argumentTypes;
    private final Class<?> commandSenderClass;

    private MethodDiscover(Executor executor, Method method, CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
        this.executor = executor;
        this.method = method;
        this.argumentTypes = Arrays.asList(method.getParameterTypes());
        this.commandSenderClass = argumentTypes.remove(0); // Should be or extends CommandSender
    }

    public static void discover(CommandExecutor commandExecutor, List<MethodExecutor> executors) {
        final Class<? extends CommandExecutor> clazz = commandExecutor.getClass();
        for(final Method method : clazz.getMethods()) {
            if(method.isAnnotationPresent(Executor.class)) {
                final MethodDiscover methodDiscover = new MethodDiscover(method.getAnnotation(Executor.class), method, commandExecutor);
                executors.add(methodDiscover);
            }
        }
    }

    public static @Nullable Object bindArgument(@NotNull String argument, Class<?> clazz) {
        if(clazz == String.class) {
            return argument;
        }
        else if(clazz == Integer.class) {
            try {
                return Integer.valueOf(argument);
            } catch (NumberFormatException ignored) {}
        }
        else if(clazz == Boolean.class) {
            if (argument.equals("true")) {
                return true;
            } else if (argument.equals("false")) {
                return false;
            }
        }
        else if(clazz == Player.class) {
            return Bukkit.getPlayer(argument);
        }

        return null;
    }

    @Override
    public Optional<Boolean> command(CommandSender commandSender, String[] arguments) {
        if(!commandSenderClass.isInstance(commandSender)) {
            return Optional.empty();
        }
        if(arguments.length != argumentTypes.size()) {
            return Optional.empty();
        }

        final Object[] bindedArguments = new Object[arguments.length];

        for(int i = 0; i < arguments.length; i++) {
            bindedArguments[i] = bindArgument(arguments[i], argumentTypes.get(i));
            if(bindedArguments[i] == null) {
                commandSender.sendMessage("Invalid argument " + i);
                return Optional.of(false);
            }
        }

        Object ret = null;
        try {

            ret = method.invoke(commandExecutor, commandSender, bindedArguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(ret instanceof Boolean) {
            return Optional.of((Boolean)ret);
        }
        else {
            return Optional.of(true);
        }
    }

    @Override
    public Optional<String> tabComplete(CommandSender commandSender, String[] arguments) {
        if(!commandSenderClass.isInstance(commandSender)) {
            return Optional.empty();
        }
        if(arguments.length == 1) {
            if(!executor.subCommand().isEmpty()) {
                return Optional.of(executor.subCommand());
            }
        }

        return Optional.empty();
    }
}
