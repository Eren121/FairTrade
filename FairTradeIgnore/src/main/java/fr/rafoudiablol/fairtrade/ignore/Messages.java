package fr.rafoudiablol.fairtrade.ignore;

import fr.rafoudiablol.internationalization.Localization;
import fr.rafoudiablol.internationalization.ParserWithPercent;
import fr.rafoudiablol.internationalization.UnlocalizedMessage;
import org.bukkit.entity.Player;

public class Messages extends Localization {
    public Messages(FairTradeIgnore plugin) {
        super(plugin, new ParserWithPercent(), plugin.getParent().messages.getLocale());
    }

    public UnlocalizedMessage.OneArgs<Player> playerAlreadyIgnored;
    public UnlocalizedMessage.OneArgs<Player> playerAdded;
    public UnlocalizedMessage.OneArgs<Integer> limitReached;

    public UnlocalizedMessage.OneArgs<Player> playerNotIgnored;
    public UnlocalizedMessage.OneArgs<Player> playerRemoved;

    public UnlocalizedMessage.OneArgs<Integer> listPlayers;

    public UnlocalizedMessage.OneArgs<Player> ignored;

}
