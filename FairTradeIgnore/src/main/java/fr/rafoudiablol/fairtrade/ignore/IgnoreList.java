package fr.rafoudiablol.fairtrade.ignore;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * Bind the file and the memory.
 * The lists are loaded only when this class is instanciated, and can also manipulate the data of the files.
 */
public class IgnoreList implements Iterable<UUID> {
    private final Yaml yaml;
    private final Player player;
    private Set<UUID> uuids;
    private final File folder;

    public IgnoreList(FairTradeIgnore plugin, Player player) {
        this.player = player;
        this.uuids = new HashSet<>();
        this.folder = new File(plugin.getDataFolder(), "lists");
        this.yaml = plugin.getYaml();
        load();
    }

    private void load() {
        final File file = getFile();
        if(file.isFile()) {
            final Object obj;

            try {
                obj = yaml.load(new BufferedInputStream(new FileInputStream(file)));
                if(obj instanceof Set) {
                    uuids = (Set<UUID>)obj;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        if(!folder.exists()) {
            folder.mkdirs();
        }

        final File file = getFile();

        try {
            final FileWriter writer = new FileWriter(file);
            yaml.dump(uuids, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public File getFile() {
        return new File(folder, player.getUniqueId()  + ".yml");
    }

    public boolean contains(Player player) {
        return uuids.contains(player.getUniqueId());
    }

    public boolean add(Player player) {
        boolean ret;
        if(ret = uuids.add(player.getUniqueId())) {
            save();
        }
        return ret;
    }

    public boolean remove(Player player) {
        boolean ret;
        if(ret = uuids.remove(player.getUniqueId())) {
            save();
        }
        return ret;
    }

    public int size() {
        return uuids.size();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<UUID> iterator() {
        return uuids.iterator();
    }
}
