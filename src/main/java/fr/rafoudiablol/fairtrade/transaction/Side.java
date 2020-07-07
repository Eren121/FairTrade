package fr.rafoudiablol.fairtrade.transaction;

import org.jetbrains.annotations.NotNull;

public enum Side {
    LOCAL,
    REMOTE;

    public static final int length = Side.values().length;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public @NotNull Side opposite() {
        return this == LOCAL ? REMOTE : LOCAL;
    }
}