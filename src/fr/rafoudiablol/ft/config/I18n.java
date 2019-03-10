package fr.rafoudiablol.ft.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class I18n {

    public static final String PWNED = "§kPaStA pASTa";
    private FileConfiguration config = null;

    public void setLangConfig(YamlConfiguration config) {
        this.config = config;
    }

    public String localize(EnumI18n unlocalized, Object... args) {
        return config != null ? unlocalized.replaceArgs(config.getString(unlocalized.path), args) : PWNED;
    }

    public List<String> localizeList(EnumI18n unlocalized, Object... args) {

        if(config == null)
            return Collections.singletonList(PWNED);

        List<String> list = config.getStringList(unlocalized.path);
        List<String> listString = new LinkedList<>();

        for(String str : list) {

            listString.add(unlocalized.replaceArgs(str, args));
        }

        return listString;
    }
}