package fr.rafoudiablol.plugin;

import org.jetbrains.annotations.Nullable;

public class Booleans {
    /**
     * Static class
     */
    private Booleans() {}

    /**
     * @return true if str equals exactly "true"
     *         false if str equals exactly "false"
     *         otherwise returns null
     */
    public static @Nullable Boolean valueOf(String str) {
        switch(str) {
            case "true":
                return true;
            case "false":
                return false;
            default:
                return null;
        }
    }
}
