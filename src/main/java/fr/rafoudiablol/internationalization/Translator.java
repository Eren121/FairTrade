package fr.rafoudiablol.internationalization;

import fr.rafoudiablol.internationalization.discovery.Discovery;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator {
    private final List<Discovery> discoveries = new ArrayList<>();
    private final Map<String, String> loadedSymbols = new HashMap<>();
    private final Plugin plugin;

    public Translator(Plugin plugin) {
        this.plugin = plugin;
    }

    public void addDiscovery(Discovery discovery) {
        discoveries.add(discovery);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void loadTranslations() {
        for(Discovery discovery : discoveries) {
            discovery.discoverTranslations(this);
        }
    }

    public void setSymbol(String key, String value) {
        loadedSymbols.put(key, value);
    }

    public @Nullable String getSymbol(String key) {
        return loadedSymbols.get(key);
    }
}
