package fr.rafoudiablol.internationalization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Litteral implements MessagePart {
    private final @Nullable String value;

    public Litteral(@NotNull String value) {
        this.value = value;
    }

    @Override
    public String translate(Context context) {
        return value;
    }
}
