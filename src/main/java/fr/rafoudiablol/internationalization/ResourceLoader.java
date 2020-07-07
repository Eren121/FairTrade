package fr.rafoudiablol.internationalization;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.*;

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

    /**
     * @param save true to save the resource if it's in plugin binary but not in plugin data director
     *             false to not save the resource whatever the resource exists anywhere.
     */
    public @Nullable InputStream getResource(String path, boolean save) {
        InputStream stream = null;

        if(!existsInDataFolder(path)) {
            if(existsInJAR(path)) {
                plugin.saveResource(path, false);
                stream = plugin.getResource(path);
            }
        }
        else {
            try {
                stream = new BufferedInputStream(new FileInputStream(
                    new File(plugin.getDataFolder(), path)
                ));
            } catch (FileNotFoundException e) {}
        }

        return stream;
    }
}
