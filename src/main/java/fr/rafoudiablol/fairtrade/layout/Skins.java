package fr.rafoudiablol.fairtrade.layout;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Serializable list of skins
 * Contains a list of skin, eachskin can have a default value
 * If a skin contains a default value, the YAML-value is of type ItemStack containing the default item.
 * If a skin does nto contains a default value, the YAML-value is of a type List with keys true/false containing each item.
 *
 * Also, when having a default item, you can't have a specific item (only 2 possibilities, so it would not be very useful)
 */
public class Skins implements ConfigurationSerializable {
    private final Map<String, SlotSkin> skins = new HashMap<>();

    public void addSkin(@NotNull String key, @NotNull SlotSkin skin) {
        skins.put(key, skin);
    }

    public @Nullable SlotSkin getSkin(@NotNull String key) {
        return skins.get(key);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        final Map<String, Object> serialized = new HashMap<>();
        skins.forEach((key, skin) -> {
            serialized.put(key, skin.serialize());
        });
        return serialized;
    }

    @NotNull
    public static Skins deserialize(@NotNull Map<String, Object> serialized) {
        final Skins skins = new Skins();
        serialized.forEach((key, value) -> {
            final SlotSkin skin = SlotSkin.deserialize(value);
            if(skin != null) {
                skins.addSkin(key, skin);
            }
        });
        return skins;
    }
}
