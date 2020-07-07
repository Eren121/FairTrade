package fr.rafoudiablol.fairtrade;

import fr.rafoudiablol.fairtrade.guards.MaximalDistanceGuard;
import fr.rafoudiablol.fairtrade.guards.SameWorldGuard;
import fr.rafoudiablol.fairtrade.guards.SelfTradingGuard;
import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.internationalization.Translatable;
import fr.rafoudiablol.plugin.ResourceLoader;
import fr.rafoudiablol.fairtrade.layout.LayoutLoader;
import fr.rafoudiablol.fairtrade.layout.Skins;
import fr.rafoudiablol.fairtrade.layout.SlotSkin;
import fr.rafoudiablol.fairtrade.providers.ExactMatchSlotProvider;
import fr.rafoudiablol.fairtrade.providers.ItemSlotProvider;
import fr.rafoudiablol.fairtrade.providers.ResourceSlotProvider;
import fr.rafoudiablol.fairtrade.resources.Experience;
import fr.rafoudiablol.fairtrade.resources.VaultCurrency;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.waiting.WaitingScreen;
import fr.rafoudiablol.fairtrade.slots.ConfirmSlot;
import fr.rafoudiablol.fairtrade.slots.EmptySlot;
import fr.rafoudiablol.fairtrade.slots.StatusSlot;
import fr.rafoudiablol.internationalization.Localization;
import fr.rafoudiablol.plugin.ClassLoader;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class FairTrade extends JavaPlugin implements Translatable {
    public static final String LAYOUT_PATH = "layout";
    public static final String SKINS_PATH = "items";
    public static final String SLOT_SEPARATOR = ",";
    public static final String LAYOUT_CONFIG_FILE_PATH = "layout.yml";
    public static final String EMPTY_SLOT_IDENTIFIER = "NOP";
    public static final String TOGGLE_BUTTON_SLOT_IDENTIFIER = "TOG";
    public static final String STATUS_SLOT_SUFFIX_IDENTIFIER = "ST";
    public static final String LOCAL_PREFIX_IDENTIFIER = "~";
    public static final String REMOTE_PREFIX_IDENTIFIER = "#";
    private final Map<Side, String> prefixes = new HashMap<>();
    private static Economy econ = null;


    public @NotNull String getPrefixFromSide(Side side) {
        return Objects.requireNonNull(prefixes.get(side));
    }

    public FairTrade() {
        prefixes.put(Side.LOCAL, LOCAL_PREFIX_IDENTIFIER);
        prefixes.put(Side.REMOTE, REMOTE_PREFIX_IDENTIFIER);
    }

    private final ResourceLoader resources = new ResourceLoader(this);
    private Guard guard;
    public LayoutLoader layoutLoader;
    public Messages messages;
    public WaitingScreen waitingScreen;
    public TransactionScreen transactionScreen;
    public TransactionManager transactionManager;

    private void registerCommands() {
        for(Commands command : Commands.values()) {
            Objects.requireNonNull(getCommand(command.name)).setExecutor(command.generator.apply(this));
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onEnable() {
        registerCommands();

        // Create default config.yml if this one doesn't exist
        saveDefaultConfig();

        messages = new Messages(this);
        transactionManager = new TransactionManager(this);
        waitingScreen = new WaitingScreen(this);
        guard = new Guard(this);

        layoutLoader = new LayoutLoader(this, loadLayoutConfig(), LAYOUT_PATH, SKINS_PATH, SLOT_SEPARATOR);
        addDefaultProviders();
        transactionScreen = layoutLoader.loadScreen();


        new SameWorldGuard(this);
        new MaximalDistanceGuard(this);
        new SelfTradingGuard(this);
    }

    private void addDefaultProviders() {
        layoutLoader.addProvider(new ExactMatchSlotProvider(EMPTY_SLOT_IDENTIFIER, EmptySlot::new));
        layoutLoader.addProvider(new ExactMatchSlotProvider(TOGGLE_BUTTON_SLOT_IDENTIFIER, ConfirmSlot::new));
        layoutLoader.addProvider(new ResourceSlotProvider("EXP", "experience"));
        layoutLoader.addProvider(new ResourceSlotProvider("CUR", "currency"));
        for(final Side side : Side.values()) {
            layoutLoader.addProvider(new ExactMatchSlotProvider(getPrefixFromSide(side) + STATUS_SLOT_SUFFIX_IDENTIFIER,
                    (screen, rawSlot) -> new StatusSlot(screen, rawSlot, side)));
            layoutLoader.addProvider(new ItemSlotProvider(getPrefixFromSide(side), side));
        }

        transactionManager.registerResource(new Experience());

        if(!setupEconomy()) {
            getLogger().info("Vault dependency not found: currency disabled, any attempt to use currency will fail");
        }
        else {
            transactionManager.registerResource(new VaultCurrency(econ));
        }
    }

    private YamlConfiguration loadLayoutConfig() {
        final File file = resources.getResource(LAYOUT_CONFIG_FILE_PATH);

        // YAML does not works if serialized classes are not loaded...

        ClassLoader.forName(Skins.class);
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


        if(!config.contains(SKINS_PATH)) {
            final Skins defaultSkins = getDefaultSkins();
            config.set(SKINS_PATH, defaultSkins);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    private Skins getDefaultSkins() {
        final Skins skins = new Skins();
        skins.addSkin("empty", new SlotSkin(new ItemStack(Material.GLASS_PANE)));
        skins.addSkin("status", new SlotSkin(new ItemStack(Material.GREEN_WOOL), new ItemStack(Material.RED_WOOL)));
        skins.addSkin("confirm", new SlotSkin(new ItemStack(Material.WRITABLE_BOOK)));
        skins.addSkin("experience", new SlotSkin(new ItemStack(Material.EXPERIENCE_BOTTLE)));
        skins.addSkin("currency", new SlotSkin(new ItemStack(Material.GOLD_ORE)));
        return skins;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public Localization getLocalization() {
        return messages;
    }
}
