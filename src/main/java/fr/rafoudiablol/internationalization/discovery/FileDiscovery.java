package fr.rafoudiablol.internationalization.discovery;

import fr.rafoudiablol.internationalization.Translator;
import fr.rafoudiablol.internationalization.discovery.syntax.FileSyntax;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Load translation from a file.
 * Specifically, from a resource inside the JAR of the plugin.
 * If the file exists in the plugin data folder, read the file from the plugin data folder.
 * If the file do not exists in the plugin data folder, search the resource inside the JAR.
 * If the resource exists in the JAR, save the resource from the JAR inside the plugin data folder and load translations
 * from it. If the file inside the JAR also doesn't exists, do not throw any error but any translation will be loaded.
 *
 * (same behavior as saveConfig() for plugins' config.yml).
 *
 * How to load the translation file is determined by the loader.
 */
public class FileDiscovery extends Discovery {
    /**
     * Path to search the file (without th eextension, defined by the syntax)
     */
    private final String path;

    /**
     * Tell how to interpret the file and to locate the file  if it has a file extension (.yml, .txt, nothing, ...).
     */
    private final FileSyntax syntax;

    public FileDiscovery(String path, FileSyntax syntax) {
        this.path = path + syntax.getFileExtension();
        this.syntax = syntax;
    }

    public String getContent(Plugin plugin) {
        try {
            return new String(Files.readAllBytes(new File(plugin.getDataFolder(), path).toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean existsInDataFolder(Plugin plugin) {
        final File file = new File(plugin.getDataFolder(), path);
        return file.isFile();
    }

    public boolean existsInJAR(Plugin plugin) {

        // Java try-with-resource works if resource is null
        // Java spec specify that object should be closed only if non-null
        // https://stackoverflow.com/questions/35372148/try-with-resource-when-autocloseable-is-null

        try(InputStream is = plugin.getResource(path)) {
            return is != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void discoverTranslations(@NotNull Translator translator) {
        final Plugin plugin = translator.getPlugin();
        final Map<String, String> translations = new HashMap<>();
        String content = null;

        if(!existsInDataFolder(plugin)) {
            if(existsInJAR(plugin)) {
                plugin.saveResource(path, false);
                content = getContent(plugin);
            }
        }
        else {
            content = getContent(plugin);
        }

        if(content != null) {
            syntax.loadTranslations(content, translations);
        }

        for(Map.Entry<String, String> entry : translations.entrySet()) {
            translator.setSymbol(entry.getKey(), entry.getValue());
        }
    }
}
