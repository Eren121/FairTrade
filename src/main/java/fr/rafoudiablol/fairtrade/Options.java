package fr.rafoudiablol.fairtrade;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.function.Function;

public enum Options {
    LANG(String.class);

    private final String key;
    private final Class<?> type;

    Options(Object defaultValue) {
        this.key = name().toLowerCase();
        this.type = defaultValue.getClass();
    }

    private void checkType(Class<?> clazz) {
        if(clazz != type) {
            throw new IllegalStateException("Invalid option class");
        }
    }

    private<T> T getAs(Class<T> clazz, Function<String, T> method) {
        checkType(clazz);
        return method.apply(key);
    }

    public String getString(FileConfiguration config) {
        return getAs(String.class, config::getString);
    }

    public int getInt(FileConfiguration config) {
        return getAs(Integer.class, config::getInt);
    }
}
