package fr.rafoudiablol.plugin;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CollectionUtils {
    private CollectionUtils() {}

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
