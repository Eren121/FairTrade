package fr.rafoudiablol.fairtrade.guards;

import fr.rafoudiablol.fairtrade.FairTrade;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SelfTradingGuard extends Guard {
    public SelfTradingGuard(FairTrade plugin) {
        super(plugin);
    }

    @Override
    protected Optional<String> getReason(Player first, Player second) {
        if(first.equals(second) && !first.hasPermission("fairtrade.selftrade")) {
            return Optional.of(plugin.messages.self.translate());
        }

        return Optional.empty();
    }
}
