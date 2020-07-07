package fr.rafoudiablol.fairtrade;

import fr.rafoudiablol.internationalization.Localization;
import fr.rafoudiablol.internationalization.UnlocalizedMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Messages extends Localization {

    public UnlocalizedMessage.NoArgs notEnoughPlace;
    public UnlocalizedMessage.NoArgs aboveDistance;
    public UnlocalizedMessage.NoArgs crossWorld;
    public UnlocalizedMessage.NoArgs self;

    public Messages(Plugin plugin) {
        super(plugin);
    }

    public UnlocalizedMessage.NoArgs playersOnly;
    public UnlocalizedMessage.OneArgs<String> playerNotFound;
    public UnlocalizedMessage.OneArgs<Player> genericReason;
    public UnlocalizedMessage.ThreeArgs<Player, String, String> ask;
    public UnlocalizedMessage.NoArgs accept;
    public UnlocalizedMessage.NoArgs refuse;

    public UnlocalizedMessage.NoArgs buttons_status_local_true;
    public UnlocalizedMessage.NoArgs buttons_status_local_false;
    public UnlocalizedMessage.OneArgs<Player> buttons_status_remote_false;
    public UnlocalizedMessage.OneArgs<Player> buttons_status_remote_true;
    public UnlocalizedMessage.NoArgs buttons_confirm_true;
    public UnlocalizedMessage.NoArgs buttons_confirm_false;

    // 1. What you give 2. What you receive 3. The difference between the two
    public UnlocalizedMessage.ThreeArgs<String, String, String> buttons_resource;

    public UnlocalizedMessage.OneArgs<Player> waitTitle;
    public UnlocalizedMessage.OneArgs<Player> tradeTitle;

    public UnlocalizedMessage.OneArgs<Player> waitItem;
}
