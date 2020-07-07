package fr.rafoudiablol.internationalization;

import java.util.regex.Pattern;

/**
 * Parser that analyze messages with argument in the form, for the n-th argument: %n
 *
 * Example:
 * The message
 * "Player %1 has connected"
 * with arguments array
 * {"Notch"}
 * will be parsed like
 * "Player Notch has connected"
 */
public class ParserWithPercent extends PatternParser {
    public ParserWithPercent() {
        super(Pattern.compile("%(\\d+)"));
    }
}
