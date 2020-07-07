import net.md_5.bungee.api.chat.TextComponent;

import static fr.rafoudiablol.xml.ChatHTML.parseXML;

public class TestXML {
    public static void main(String[] args) {

        testEmpty();
        testSingleTextNode();

        {
            String xml = "aaa<b>bbb</b>";
            TextComponent component = new TextComponent("aaa");
            TextComponent extra = new TextComponent("bbb");
            component.addExtra(extra);
            extra.setBold(true);

            test(component, xml);
        }

        {
            String xml = "<b>aaa</b>bbb";
            TextComponent component = new TextComponent("aaa");
            TextComponent extra = new TextComponent("bbb");
            component.addExtra(extra);

            component.setBold(true);
            extra.setBold(false);

            test(component, xml);
        }
    }

    public static void testEmpty() {
        test(new TextComponent(), "");
    }

    public static void testSingleTextNode() {
        String text = "aaa";
        test(new TextComponent(text), text);
    }

    public static void test(TextComponent component, String xmlSameExpected) {
        String first = component.toLegacyText();
        String second = parseXML(xmlSameExpected).toLegacyText();

        System.out.println(first + " == " + second + ": " + (first.equals(second) ? "ok" : "error"));
    }
}
