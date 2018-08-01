package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.container.Skeleton;
import fr.rafoudiablol.ft.main.ILoggeable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.io.*;

import static fr.rafoudiablol.ft.container.Skeleton.initInv;
import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class Options implements IOptions {

    private JavaPlugin plugin;
    private int[] skeSlots;
    private I18n i18n;

    public <T extends JavaPlugin & ILoggeable>Options(T plugin) {
        this.plugin = plugin;

        i18n = new I18n();
        String locale = getConfig().getString(EnumOption.LANG.path);
        File langFolder = new File(plugin.getDataFolder(), "lang");
        File langFile = new File(langFolder, "lang_" + locale + ".yml");
        File fileSkeleton = new File(plugin.getDataFolder(), "trading.txt");

        if(!langFolder.exists())
        {
            plugin.i("Creating language folder " + langFolder.getName() + "/...");
            langFolder.mkdirs();
        }
        if(!langFile.exists()) {
            plugin.saveResource("lang/lang_" + locale + ".yml", false);
        }
        if (!fileSkeleton.exists()) {
            plugin.saveResource("trading.txt", false);
        }

        i18n.setLangConfig(loadConfiguration(langFile));

        try {
            BufferedReader in = new BufferedReader(new FileReader(fileSkeleton));
            StringBuilder tmp = new StringBuilder();
            int lineNo = 0;
            String line;

            while((line = in.readLine()) != null) {
                line = line.trim();
                lineNo++;
                if(!line.startsWith("#")) {

                    if(line.length() != 17) {

                        throw new IOException("invalid nb. of characters: " + line.length() + " line n." + lineNo);
                    }

                    char c;
                    for(int i = 0; i < line.length(); ++i) {
                        c = line.charAt(i);
                        if(c >= '0' && c <= '9') {

                            tmp.append(c);
                        }
                        else if(c != ' ' || (c == ',' && (i == 0))) {

                            throw new IOException("invalid character, line " + lineNo + "-" + i);
                        }
                    }
                }
            }

            skeSlots = new int[tmp.length()];

            for(int i = 0; i < tmp.length(); ++i) {
                skeSlots[i] = tmp.charAt(i);
            }

        } catch (IOException e) {
            plugin.w("cannot load trading.txt");
            e.printStackTrace();
            skeSlots = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        }

        initInv(skeSlots);
    }

    @Override
    public double getDistanceMax() {
        return getConfig().getDouble(EnumOption.DISTANCE.path);
    }

    @Override
    public void setDistanceMax(double f) {
        setAndSave(EnumOption.DISTANCE, f);
    }

    @Override
    public I18n geti18n() {
        return i18n;
    }

    @Override
    public ItemStack getDummyItem(boolean ok) {

        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE);
        i.setDurability((short)(ok ? 13 : 14));
        return i;
    }

    @Override
    public int[] getSkeSlots() {
        return skeSlots;
    }

    private void setAndSave(EnumOption opt, Object o) {
        getConfig().set(opt.path, o);
        saveConfig();
    }

    private FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    private void saveConfig() {
        plugin.saveConfig();
    }
}
