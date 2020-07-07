package fr.rafoudiablol.fairtrade.resources;

import fr.rafoudiablol.fairtrade.transaction.TradeResource;
import fr.rafoudiablol.plugin.Numbers;
import fr.rafoudiablol.plugin.ExperienceAPI;
import org.bukkit.entity.Player;

public class Experience extends TradeResource {
    @Override
    public String getName() {
        return "experience";
    }

    @Override
    public String formatResource(double quantity, boolean difference) {
        final int xp = (int)quantity;
        final String format = difference ? Numbers.forceSign(xp) : String.valueOf(xp);
        return format + "xp";
    }

    @Override
    public double clamp(double xp, Player player) {
        return Numbers.clamp(0, ExperienceAPI.getExp(player), (int)xp);
    }

    @Override
    public void give(Player player, double quantity) {
        ExperienceAPI.changeExp(player, (int)quantity);
    }
}
