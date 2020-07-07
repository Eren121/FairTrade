package fr.rafoudiablol.xml;

import fr.rafoudiablol.plugin.CollectionUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeVisitor;

import java.util.Stack;

public class ChatHTML {
    private ChatHTML() {}

    public static void transform(Node node, TextComponent component) {
        switch(node.nodeName()) {

            /**
             * Styling
             */

            case "k":
                component.setObfuscated(true);
                break;

            case "b":
                component.setBold(true);
                break;

            case "s":
                component.setStrikethrough(true);
                break;

            case "u":
                component.setUnderlined(true);
                break;

            case "i":
                component.setItalic(true);
                break;

            /**
             * Colors
             */

            case "x0":
            case "black":
                component.setColor(ChatColor.BLACK);
                break;

            case "x1":
            case "darkblue":
                component.setColor(ChatColor.DARK_BLUE);
                break;

            case "x2":
            case "darkgreen":
                component.setColor(ChatColor.DARK_GREEN);
                break;

            case "x3":
            case "darkaqua":
                component.setColor(ChatColor.DARK_AQUA);
                break;

            case "x4":
            case "darkred":
                component.setColor(ChatColor.DARK_RED);
                break;

            case "x5":
            case "darkpurple":
                component.setColor(ChatColor.DARK_PURPLE);
                break;

            case "x6":
            case "gold":
                component.setColor(ChatColor.GOLD);
                break;

            case "x7":
            case "gray":
                component.setColor(ChatColor.GRAY);
                break;

            case "x8":
            case "darkgray":
                component.setColor(ChatColor.DARK_GRAY);
                break;

            case "x9":
            case "blue":
                component.setColor(ChatColor.BLUE);
                break;

            case "xa":
            case "green":
                component.setColor(ChatColor.GREEN);
                break;

            case "xb":
            case "aqua":
                component.setColor(ChatColor.AQUA);
                break;

            case "xc":
            case "red":
                component.setColor(ChatColor.RED);
                break;

            case "xd":
            case "lightpurple":
                component.setColor(ChatColor.LIGHT_PURPLE);
                break;

            case "xe":
            case "yellow":
                component.setColor(ChatColor.YELLOW);
                break;

            case "xf":
            case "white":
                component.setColor(ChatColor.WHITE);
                break;

            default:
                if(node instanceof TextNode) {
                    TextNode text = (TextNode)node;
                    component.setText(text.getWholeText());
                }
        }
    }

    public static TextComponent parseXML(String xml) {
        final Document document = Jsoup.parse(xml, "", Parser.xmlParser());
        final Stack<TextComponent> stack = new Stack<>();

        document.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {

                if(depth == 0) {
                    stack.push(new TextComponent());
                }
                else {
                    final TextComponent text = new TextComponent();
                    transform(node, text);
                    stack.push(text);
                }
            }

            @Override
            public void tail(Node node, int depth) {
                if(depth > 0) {
                    final TextComponent leaf = stack.pop();
                    final TextComponent base = stack.peek();

                    // Transform each Leaf into another Leaf
                    // That is due of how TextComponent handle compound text
                    // This work without it but its cleaner, avoiding repeated useless chat formatting characters.

                    if(node instanceof TextNode) {
                        if(base.getText().isEmpty() && CollectionUtils.isEmpty(base.getExtra())) {
                            base.setText(leaf.getText());
                        }
                        else {
                            base.addExtra(leaf);
                        }
                    }
                    else {
                        base.addExtra(leaf);
                    }
                }
            }
        });

        return stack.empty() ? new TextComponent() : stack.pop();
    }
}
