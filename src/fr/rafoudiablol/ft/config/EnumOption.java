package fr.rafoudiablol.ft.config;

public enum EnumOption {

    DISTANCE("max-trade-distance"),
    LANG("lang"),
    SELF_TRADING("self");

    public final String path;

    EnumOption(String key) {
        this.path = key;
    }
}
