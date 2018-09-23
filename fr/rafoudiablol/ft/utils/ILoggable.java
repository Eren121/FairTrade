package fr.rafoudiablol.ft.utils;

import java.util.logging.Logger;

public interface ILoggable {

    Logger getLogger();

    default void w(Object msg) {
        getLogger().warning(String.valueOf(msg));
    }

    default void i(Object msg) {
        getLogger().info(String.valueOf(msg));
    }
}
