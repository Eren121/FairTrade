package fr.rafoudiablol.fairtrade.transaction;

import org.jetbrains.annotations.NotNull;

public enum Protagonist {
    INITIATOR,
    REPLIER;

    public @NotNull Protagonist opposite() {
        return this == INITIATOR ? REPLIER : INITIATOR;
    }

    public @NotNull Protagonist get(@NotNull Side side) {
        return side == Side.LOCAL ? this : opposite();
    }
}
