package fr.rafoudiablol.plugin;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Use to get resource from plugin, with possibility to save them.
 * The two possibilities of a resource location are inside plugin binary (JAR in Java) or in data folder, with some
 * rules.
 *
 * All paths directory separators should be a slash '/'.
 */
public class ResourceLoader {
    private final Plugin plugin;

    public ResourceLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean existsInDataFolder(String path) {
        final File file = new File(plugin.getDataFolder(), path);
        return file.isFile();
    }

    public boolean existsInJAR(String path) {

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
    
    public @NotNull File getResource(String path) {
        InputStream stream = null;

        if(!existsInDataFolder(path)) {
            if(existsInJAR(path)) {
                plugin.saveResource(path, false);
                stream = plugin.getResource(path);
            }
        }

        if(!existsInDataFolder(path)) {
            plugin.getLogger().severe("resource '" + path + "' not found in plugin data folder.");
        }

        return new File(plugin.getDataFolder(), path);
    }
}
