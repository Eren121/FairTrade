package fr.rafoudiablol.internationalization;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnlocalizedMessage {
    public static final String DEFAULT_MESSAGE = "<Missing String>";
    private @NotNull String name;
    private @NotNull List<MessagePart> messageParts;

    public UnlocalizedMessage() {
        this.name = "";
        this.messageParts = Lists.newArrayList();
    }

    public UnlocalizedMessage(@NotNull String name, @NotNull List<MessagePart> parts) {
        this.name = name;
        this.messageParts = parts;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setParts(@NotNull List<MessagePart> parts) {
        this.messageParts = parts;
    }

    // Needed to translat complex text with JSON chats (with, multiple commands, url etc)
    public List<MessagePart> getParts() {
        return messageParts;
    }

    /**
     * Protected to prevent errors of wrong number of arguments call.
     * Subclasses should provide an explicit method binding to this one with right number of arguments,
     * that is the number of arguments are checked at compile time.
     */
    protected String translate(Object... objects) {
        final String[] params = new String[objects.length];
        for(int i = 0; i < params.length; i++) {
            if(objects[i] instanceof CommandSender) {
                params[i] = ((CommandSender)objects[i]).getName();
            }
            else if(objects[i] == null) {
                params[i] = "";
            }
            else {
                params[i] = objects[i].toString();
            }
        }
        return translate(params);
    }

    public Context getContext(String... args) {
        return new Context(this, args);
    }

    protected final String translate(String... parameters) {
        final Context context = getContext(parameters);
        final StringBuilder sb = new StringBuilder();

        for(MessagePart messagePart : messageParts) {
            sb.append(messagePart.translate(context));
        }

        return sb.toString();
    }

    public String name() {
        return name;
    }

    public static class NoArgs extends UnlocalizedMessage {
        public String translate() {
            return super.translate();
        }
    }

    public static class OneArgs<T> extends UnlocalizedMessage {
        public String translate(T t) {
            return super.translate(t);
        }
    }

    public static class TwoArgs<T, U> extends UnlocalizedMessage {
        public String translate(T t, U u) {
            return super.translate(t, u);
        }
    }

    public static class ThreeArgs<T, U, V> extends UnlocalizedMessage {
        public String translate(T t, U u, V v) {
            return super.translate(t, u, v);
        }
    }


    /**
     * Middly unreadable
     */
    public static class FourArgs<T, U, V, W> extends UnlocalizedMessage {
        public String translate(T t, U u, V v, W w) {
            return super.translate(t, u, v, w);
        }
    }

    /**
     * Almost unreadable
     */
    public static class FiveArgs<T, U, V, W, X> extends UnlocalizedMessage {
        public String translate(T t, U u, V v, W w, X x) {
            return super.translate(t, u, v, w, x);
        }
    }

    /**
     * Basically unreadable
     */
    public static class SixArgs<T, U, V, W, X, Y> extends UnlocalizedMessage {
        public String translate(T t, U u, V v, W w, X x, Y y) {
            return super.translate(t, u, v, w, x, y);
        }
    }

    /**
     * Totally unreadable
     *
     * To answer the question "why stop here ?" its because Z is the last letter of the alphabet.
     */
    public static class SevenArgs<T, U, V, W, X, Y, Z> extends UnlocalizedMessage {
        public String translate(T t, U u, V v, W w, X x, Y y, Z z) {
            return super.translate(t, u, v, w, x, y, z);
        }
    }

}
