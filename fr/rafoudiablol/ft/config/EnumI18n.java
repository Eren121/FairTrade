package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.entity.Player;

import static fr.rafoudiablol.ft.config.I18n.PWNED;

public enum EnumI18n {

    WELCOME("<name>"),
    PREFIX,
    NO_REQUEST,
    NO_TRANSACTION("<id>"),
    CONFIRM,
    TITLE("<left>", "<right>", "<date>"),
    INFO_0("<left", "<right>", "<date>"),
    INFO_1("<left", "<right>", "<date>"),
    INFO_2("<left", "<right>", "<date>"),
    INFO_3("<left", "<right>", "<date>"),
    REQUEST("<player>"),
    REASON_WORLD,
    REASON_DISTANCE,
    FINALIZED("<id>"),

    BUTTON_CANCEL,
    BUTTON_CONFIRM,

    YOU_ACCEPTED,
    REMOTE_ACCEPTED("<player>"),
    NOBODY_ACCEPTED,

    XP_AMOUNT("<xp>");

    public final String path;
    public final String[] args;

    EnumI18n(String... args) {
        this.path = this.name().toLowerCase();
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
}
