package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.entity.Player;

import java.util.List;

import static fr.rafoudiablol.ft.config.I18n.PWNED;

public enum EnumI18n {

    WELCOME("<name>"),
    PREFIX,
    NO_REQUEST,
    NO_TRANSACTION("<id>"),
    CONFIRM,
    REQUEST("<player>"),
    REASON_WORLD,
    REASON_DISTANCE,
    TITLE("<player>"),

    FINALIZED("<id>"),


    BUTTON_CANCEL,
    BUTTON_CONFIRM,

    YOU_ACCEPTED,
    REMOTE_ACCEPTED("<player>"),
    NOBODY_ACCEPTED,

    MONEY_GIVE("<money>"),
    MONEY_GET("<money>"),

    LOG_BRIEF("<p0>", "<p1>", "<money0>", "<money1>", "<date>");

    public final String path;
    protected final String[] args;

    EnumI18n(String... args) {
        this.path = this.name();
        this.args = args;
    }

    public String replaceArgs(String localized, Object... params) {

        if(localized == null) { // String to found

            return PWNED;
        }

        for(int i = 0; i < params.length; ++i) {

            if(params[i] instanceof Player)
                params[i] = ((Player)params[i]).getDisplayName();

            localized = localized.replace(args[i], String.valueOf(params[i]));
        }

        return localized;
    }

    public String localize(Object... args) {
        return FairTrade.getFt().getOptions().geti18n().localize(this, args);
    }

    public List<String> localizeList(Object... args) {
        return FairTrade.getFt().getOptions().geti18n().localizeList(this, args);
    }
}
