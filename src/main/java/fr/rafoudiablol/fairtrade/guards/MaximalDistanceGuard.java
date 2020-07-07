package fr.rafoudiablol.fairtrade.guards;

import fr.rafoudiablol.fairtrade.FairTrade;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MaximalDistanceGuard extends Guard {
    private boolean enabled;
    private int maxDistance;

    public MaximalDistanceGuard(FairTrade plugin) {
        super(plugin);
        maxDistance = plugin.getConfig().getInt("maximum-distance", 12);
        enabled = (maxDistance > 0);

        plugin.getLogger().info("Maximum trade distance: " + (enabled ? maxDistance : "disabled"));
    }

    @Override
    protected Optional<String> getReason(Player first, Player second) {
        if(enabled) {
            if(!first.getWorld().equals(second.getWorld()) || first.getLocation().distance(second.getLocation()) > maxDistance) {
                return Optional.of(plugin.messages.aboveDistance.translate());
            }
        }

        return Optional.empty();
    }
}
