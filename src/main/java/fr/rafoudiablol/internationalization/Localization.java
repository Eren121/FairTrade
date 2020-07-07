package fr.rafoudiablol.internationalization;

import com.google.common.base.CaseFormat;
import fr.rafoudiablol.internationalization.discovery.FileDiscovery;
import fr.rafoudiablol.internationalization.discovery.syntax.YAMLFileSyntax;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public abstract class Localization {
    private final Set<UnlocalizedMessage> messages = new HashSet<>();
    private final String locale;
    public static final String DEFAULT_LOCALE = "en";
    public static final String DEFAULT_LANG_FOLDER = "lang";
    public static final String PLUGIN_CONFIG_LOCALE_KEY = "lang";

    public String getLocale() {
        return locale;
    }

    public Localization(Plugin plugin) {
        this(plugin, new ParserWithPercent(),  plugin.getConfig().getString(PLUGIN_CONFIG_LOCALE_KEY, DEFAULT_LOCALE));
    }

    public Localization(Plugin plugin, Parser parser, String locale) {
        this.locale = locale;
        loadMessages();
        loadTranslation(plugin, parser);
    }

    protected String fieldToKey(String fieldName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, fieldName);
    }

    protected void loadMessages() {
        for(Field field : getClass().getDeclaredFields()) {
            final boolean isStatic = Modifier.isStatic(field.getModifiers());
            final boolean isInstance = UnlocalizedMessage.class.isAssignableFrom(field.getType());
            if(!isStatic && isInstance) {

                // Instanciate field (should have default constructor), if not already instanciated

                try {
                    if(field.get(this) == null) {
                        field.set(this, field.getType().getConstructor().newInstance());
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    continue;
                }

                // Get field to initialize values
                // Can't use constructor because it only has a default one (to avoid having to override constructor in each sub-class)

                try {
                    final UnlocalizedMessage message = (UnlocalizedMessage)field.get(this);

                    // field should not be static, so with Java conventions, camelCase

                    // For nested config, use "_" as separator

                    message.setName(fieldToKey(field.getName()).replaceAll("_", "."));

                    messages.add(message);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Default localization method
     * Search files inside YAML file in the lang/ directory of the plugin by default.
     * For a given locale, the coresponding <locale>.yml should exists inside the lang/ folder.
     */
    protected void loadTranslation(Plugin plugin, Parser parser) {
        Translator translator = new Translator(plugin);
        translator.addDiscovery(new FileDiscovery(DEFAULT_LANG_FOLDER + '/' + locale, new YAMLFileSyntax()));

        loadTranslation(plugin, parser, translator);
    }

    protected void loadTranslation(Plugin plugin, Parser parser, Translator translator) {
        translator.loadTranslations();

        for(UnlocalizedMessage unlocalized : messages) {
            final String symbol = translator.getSymbol(unlocalized.name());

            if(symbol == null) {
                plugin.getLogger().warning(
                        String.format("Warning: translation message '%s' for plugin %s not found. Translation will not work.",
                                unlocalized.name(),
                                plugin.getName()));
            }
            else {
                unlocalized.setParts(parser.parse(symbol));
            }
        }
    }
}