package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.utils.ILoggable;
import fr.rafoudiablol.ft.spy.SkeletonLog;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class Options implements IOptions {

    private JavaPlugin plugin;
    private SkeletonTrade ske;
    private SkeletonLog skeForLog;
    private I18n i18n;

    public <T extends JavaPlugin & ILoggable>Options(T plugin) {
        this.plugin = plugin;

        i18n = new I18n();
        String locale = getConfig().getString(EnumOption.LANG.path, "en");
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

        int lineNo = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileSkeleton));
            StringBuilder tmp = new StringBuilder();
            String line;

            while((line = in.readLine()) != null) {
                line = line.trim();
                lineNo++;
                if(!line.startsWith("#") && !line.isEmpty()) {

                    char c;
                    for(int i = 0; i < line.length(); ++i) {
                        c = line.charAt(i);
                        if(c >= '0' && c <= '9') {

                            tmp.append(c);
                        }
                        else if(c == ',') {

                            if(i == 0)
                                throw new IOException("invalid character, line " + lineNo + "-" + i);
                        }
                        else if(c != ' ') {

                            throw new IOException("invalid character, line " + lineNo + "-" + i);
                        }
                    }
                }
            }

            int[] slots = new int[tmp.length()];
            for(int i = 0; i < tmp.length(); ++i) {
                slots[i] = tmp.charAt(i) - '0';
            }

            ske = new SkeletonTrade(slots);

        } catch (IOException e) {

            StackTraceElement origins[] = e.getStackTrace();
            StackTraceElement s[] = new StackTraceElement[origins.length + 1];
            s[0] = new StackTraceElement("trading", "txt", "trading.txt", lineNo);
            System.arraycopy(origins, 0, s, 1, s.length - 1);
            plugin.w("cannot load trading.txt");
            e.setStackTrace(s);
            e.printStackTrace();
            ske = new SkeletonTrade();
        }

        skeForLog = new SkeletonLog();
    }

    @Override
    public double getDistanceMax() {
        return getConfig().getDouble(EnumOption.DISTANCE.path, 10);
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

        return new ItemStack(ok ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE);
    }

    @Override
    public boolean canSelfTrade() {
        return getConfig().getBoolean("self", false);
    }

    @Override
    public SkeletonTrade getSkeleton() {
        return ske;
    }

    @Override
    public SkeletonLog getSkeletonForLog() {
        return skeForLog;
    }

    @Override
    public ItemStack getEmptyItem() {
        return new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
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
