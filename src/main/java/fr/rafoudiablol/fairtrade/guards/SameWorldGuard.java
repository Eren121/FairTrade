package fr.rafoudiablol.fairtrade.guards;

import fr.rafoudiablol.fairtrade.FairTrade;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SameWorldGuard extends Guard {
    private boolean enabled;

    public SameWorldGuard(FairTrade plugin) {
        super(plugin);
        enabled = !plugin.getConfig().getBoolean("cross-world", false);

        plugin.getLogger().info("Cross-world trading: " + enabled);
    }

    @Override
    protected Optional<String> getReason(Player first, Player second) {
        if(enabled) {
            if(!first.getWorld().equals(second.getWorld())) {
                return Optional.of(plugin.messages.crossWorld.translate());
            }
        }

        return Optional.empty();
    }
}
