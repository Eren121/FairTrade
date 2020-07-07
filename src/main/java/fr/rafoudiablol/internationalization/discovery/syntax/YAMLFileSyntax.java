package fr.rafoudiablol.internationalization.discovery.syntax;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * Load translations from a YAML file, from a pair key-value.
 * Each value can be a string or an array, and if it's an array, the array is transformed into a string by
 * concatenating each array element with a new line character ('\n') between each element.
 *
 * The depth is not allowed inside these files.
 */
public class YAMLFileSyntax extends FileSyntax {
    @Override
    public @NotNull String getFileExtension() {
        return ".yml";
    }

    @Override
    public void loadTranslations(String content, @NotNull Map<String, String> translations) {
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(new StringReader(content));

        for(String path : config.getKeys(true)) {
            String stringValue = null;

            if(config.isList(path)) {
                final List<String> list = config.getStringList(path);
                stringValue = String.join("\n", list);
            }
            else if(!config.isConfigurationSection(path)) {
                stringValue = config.getString(path);
            }

            if(stringValue != null) {
                translations.put(path, stringValue);
            }
        }
    }
}
