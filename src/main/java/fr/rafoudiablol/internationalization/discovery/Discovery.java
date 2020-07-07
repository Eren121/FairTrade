package fr.rafoudiablol.internationalization.discovery;

import fr.rafoudiablol.internationalization.Translator;
import org.jetbrains.annotations.NotNull;

/**
 * Where to search translation messages
 */
public abstract class Discovery {
    public abstract void discoverTranslations(@NotNull Translator translator);
}
