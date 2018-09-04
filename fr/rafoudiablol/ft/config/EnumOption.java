package fr.rafoudiablol.ft.config;

public enum EnumOption {

    DISTANCE("max-trade-distance"),
    LANG("lang"),
    SELF_TRADING("self"),
    XP_AMOUNT("xp-amount");

    public final String path;

    EnumOption(String key) {
        this.path = key;
    }
}
