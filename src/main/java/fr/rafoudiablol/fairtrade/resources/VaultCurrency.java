package fr.rafoudiablol.fairtrade.resources;

import fr.rafoudiablol.fairtrade.transaction.TradeResource;
import fr.rafoudiablol.plugin.Numbers;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class VaultCurrency extends TradeResource {
    private final Economy economy;

    public VaultCurrency(Economy economy) {
        this.economy = economy;
    }

    @Override
    public String getName() {
        return "currency";
    }

    @Override
    public String formatResource(double quantity, boolean difference) {
        final String format = economy.format(quantity);
        if(difference && quantity > 0) {
            return "+" + format;
        }
        else {
            return format;
        }
    }

    @Override
    public double clamp(double quantity, Player player) {
        return Numbers.clamp(0, economy.getBalance(player), quantity);
    }

    @Override
    public void give(Player player, double quantity) {
        if(quantity < 0) {
            economy.withdrawPlayer(player, -quantity);
        }
        else if(quantity > 0) {
            economy.depositPlayer(player, quantity);
        }
    }
}
