package fr.rafoudiablol.fairtrade.layout;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.slots.EmptySlot;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionSlot;
import fr.rafoudiablol.screen.Screen;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MAIN CLASS********************
 * Define the Layout of the GUI
 *
 * Main YAML loader
 * yaml <-> Gui conversion manager
 */
public class LayoutLoader {
    private final String layoutPath;
    private final String skinsPath;
    private final String slotSeparator;

    /**
     * All registered providers to bind an identifier to a concrete slot instance.
     * It bind interface and not directly String as keys because providers can provide multiple identifiers
     * for similar slots (used for items and status), that share similar options.
     */
    protected final Skins skins;
    protected final YamlConfiguration config;
    protected final List<SlotProvider> providers = new ArrayList<>();
    protected final FairTrade plugin;
    protected List<String> rawSlots;
    protected int nbLines;

    public @NotNull Skins getSkins() {
        return skins;
    }

    public LayoutLoader(FairTrade plugin, YamlConfiguration config, String layoutPath, String skinsPath, String slotSeparator) {
        this.plugin = plugin;
        this.config = config;
        this.layoutPath = layoutPath;
        this.skinsPath = skinsPath;
        this.slotSeparator = slotSeparator;
        this.skins = config.getSerializable(skinsPath, Skins.class, new Skins());
        loadLayoutString(config.getStringList(layoutPath));
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void addProvider(SlotProvider provider) {
        providers.add(provider);
    }

    protected void loadLayoutString(List<String> lines) {
        this.rawSlots = new ArrayList<>(lines.size() * Screen.NB_SLOTS_PER_LINE);
        this.nbLines = lines.size();

        for(String line : lines) {
            final String[] lineSlots = line.split(slotSeparator);

            if(lineSlots.length != Screen.NB_SLOTS_PER_LINE) {
                final String message = "Invalid line in the trading layout: the number of slots must be exactly 9 by line";
                plugin.getLogger().severe(message);
                throw new RuntimeException(message);
            }

            rawSlots.addAll(Arrays.asList(lineSlots));
        }
    }

    protected @NotNull TransactionSlot loadSlot(String identifier, TransactionScreen screen, int rawSlot) {
        for(SlotProvider provider : providers) {
            final TransactionSlot slot = provider.createSlot(identifier, screen, rawSlot);
            if(slot != null) {
                return slot;
            }
        }

        plugin.getLogger().severe(("invalid slot identifier, setting to an empty locked slot"));
        return new EmptySlot(screen, rawSlot);
    }

    public TransactionScreen loadScreen() {
        final TransactionScreen screen = new TransactionScreen(nbLines, plugin);

        for(int i = 0; i < rawSlots.size(); i++) {
            final String identifier = rawSlots.get(i);
            final TransactionSlot slot = loadSlot(identifier, screen, i);

            screen.setSlot(i, slot);
        }

        screen.getCache().update(screen);
        return screen;
    }
}
