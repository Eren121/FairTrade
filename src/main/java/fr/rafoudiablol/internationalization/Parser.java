package fr.rafoudiablol.internationalization;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Parser to parse **not to localize messages**, but to analyze unlocalized messages and build associated Message
 * instance to optimize and not having to analyze unlocalized messages at each translation of a String (where are
 * arguments, etc).
 * This classe does not define any grammar to let different subclasses to define its own possible grammar (how to
 * identify each arguments).
 */
public interface Parser {
    @NotNull List<MessagePart> parse(String source);
}
