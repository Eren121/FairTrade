package fr.rafoudiablol.internationalization.discovery.syntax;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Load translations from a String (content of a file), with a defined syntax.
 */
public abstract class FileSyntax {
    abstract public @NotNull String getFileExtension();
    abstract public void loadTranslations(String content, @NotNull Map<String, String> translations);
}
