import fr.rafoudiablol.internationalization.MessagePart;
import fr.rafoudiablol.internationalization.ParserWithPercent;
import fr.rafoudiablol.internationalization.UnlocalizedMessage;

import java.util.List;

public class TestParserWithPercent {
    public static class MessageTest extends UnlocalizedMessage {
        private static int counter = 1;

        public MessageTest(List<MessagePart> messageParts) {
            super("msg" + (++counter), messageParts);
        }

        public String localize(String... parameters) {
            return translate(parameters);
        }
    }

    public static void main(String[] args) {
        final ParserWithPercent parser = new ParserWithPercent();
        final MessageTest msg1 = new MessageTest(parser.parse("Hello %1, how are you?"));
        final MessageTest msg2 = new MessageTest(parser.parse("Hello, how are you?"));
        final MessageTest msg3 = new MessageTest(parser.parse("%1"));
        final MessageTest msg4 = new MessageTest(parser.parse("%1 ____"));
        final MessageTest msg5 = new MessageTest(parser.parse("____ %1"));
        final MessageTest msg6 = new MessageTest(parser.parse("%1___%2"));
        final MessageTest msg7 = new MessageTest(parser.parse("%2___%1"));
        final MessageTest msg8 = new MessageTest(parser.parse("%1___%1"));

        System.out.println(msg1.localize("aaaaaaaaa"));
        System.out.println(msg2.localize()); // Ok, 0 args and 0 args given
        System.out.println(msg2.localize("aaaa")); // Ok, 0 args and 1 given (no check are made but an implementation can make a compile time check with its own localize() method)
        System.out.println(msg3.localize("aaaaaa"));
        System.out.println(msg4.localize("aaaa"));
        System.out.println(msg5.localize("aaaa"));
        System.out.println(msg6.localize("aaaa", "bbbb"));
        System.out.println(msg7.localize("aaaa", "bbbb"));
        System.out.println(msg8.localize("aaaa"));
    }
}
