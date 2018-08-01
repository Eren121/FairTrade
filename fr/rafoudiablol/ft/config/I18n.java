package fr.rafoudiablol.ft.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class I18n {

    public static final String PWNED = "§kPaStA pASTa";
    private FileConfiguration config = null;

    public void setLangConfig(YamlConfiguration config) {
        this.config = config;
    }

    public String localize(EnumI18n unlocalized, Object... args) {
        return config != null ? unlocalized.replaceArgs(config.getString(unlocalized.path), args) : PWNED;
    }
}