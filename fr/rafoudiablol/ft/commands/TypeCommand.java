package fr.rafoudiablol.ft.commands;

public enum TypeCommand {

    REQUEST("request"),
    ACCEPT("accept"),
    HISTORY("historyft");

    public final String name;

    TypeCommand(String s) {
        name = s;
    }
}
