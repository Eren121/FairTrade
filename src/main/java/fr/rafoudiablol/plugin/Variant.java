package fr.rafoudiablol.plugin;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Variant {
    /**
     * Static class
     */
    private Variant() {}

    public static<T> @NotNull T ifNull(T t, Supplier<T> supplier) {
        return t == null ? supplier.get() : t;
    }

    public static<T, U> U ifElse(boolean flag, T t, Function<T, U> ifTrue, Function<T, U> ifFalse) {
        if(flag) {
            return ifTrue.apply(t);
        }
        else {
            return ifFalse.apply(t);
        }
    }

    public static <T> void forEachDifferent(@NotNull T first, @NotNull T second, @NotNull BiConsumer<T, T> consumer) {
        if(first.equals(second)) {
            consumer.accept(first, second);
        }
        else {
            consumer.accept(first, second);
            consumer.accept(second, first);
        }
    }
}
