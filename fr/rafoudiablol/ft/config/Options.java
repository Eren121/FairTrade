package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.main.ILoggeable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Options implements IOptions {

    private JavaPlugin plugin;
    private I18n i18n;

    public <T extends JavaPlugin & ILoggeable>Options(T plugin) {
        this.plugin = plugin;

        i18n = new I18n();
        String locale = getConfig().getString(EnumOption.LANG.path);
        File langFolder = new File(plugin.getDataFolder(), "lang");
        File langFile = new File(langFolder, "lang_" + locale + ".yml");

        if(!langFolder.exists())
        {
            plugin.i("Creating language folder " + langFolder.getName() + "/...");
            langFolder.mkdirs();
        }
        if(langFile.exists())
        {
            i18n.setLangConfig(YamlConfiguration.loadConfiguration(langFile));
            plugin.w("Localization successfully loaded from folder");
        }
        else
        {
            plugin.w("Unreadable language file: " + langFile.getName());
            plugin.i("Trying to extract from plugin archive...");

            InputStream is = plugin.getResource("lang/lang_" + locale + ".yml");

            if(is != null)
            {
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(new InputStreamReader(is));
                i18n.setLangConfig(yc);
                plugin.w("Localization successfully loaded");

                try {
                    yc.save(langFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {

                plugin.w("!!error : UNREADABLE LANGUAGE!! All strings will be mind-blown");
            }
        }

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
        ChatColor c = ok ? ChatColor.GREEN : ChatColor.RED;
        i.setDurability((short)(c.getChar() - '0'));
        return i;
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
