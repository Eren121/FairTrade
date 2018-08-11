package fr.rafoudiablol.ft.commands;

public enum TypeCommand {

    REQUEST("trade"),
    ACCEPT("accept"),
    HISTORY("tradelog");

    public final String name;

    TypeCommand(String s) {
        name = s;
    }
}
