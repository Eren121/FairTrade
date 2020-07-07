package fr.rafoudiablol.internationalization;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternParser implements Parser {
    private final Pattern pattern;

    public PatternParser(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override @NotNull
    public List<MessagePart> parse(String source) {
        final List<MessagePart> messageParts = new ArrayList<>();
        final Matcher matcher = pattern.matcher(source);
        int position = 0;

        while(matcher.find()) {
            if(position != matcher.start()) {
                final String litteral = source.substring(position, matcher.start());
                messageParts.add(new Litteral(litteral));
            }

            final int index = Integer.parseInt(matcher.group(1)) - 1;
            messageParts.add(new Variable(index));

            position = matcher.end();
        }

        if(position != source.length()) {
            final String lastLitteral = source.substring(position, source.length());
            messageParts.add(new Litteral(lastLitteral));
        }

        return messageParts;
    }
}