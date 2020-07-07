package fr.rafoudiablol.fairtrade.ignore;

import fr.rafoudiablol.fairtrade.FairTrade;
import fr.rafoudiablol.fairtrade.events.AskForTransaction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Structure of the plugin
 * -----------------------
 *
 * Allow players to ignore others players so that they won't be able to send to them trading request.
 * There is multiple design possibles:
 *
 * - Allow player who ignore to send requests to player they have ignored ?
 *    * Note: some games find that like "unfair", because you can spam back people who are not be able to spam you.
 *      But it applies mostly for text messages.
 *  ==> YES
 *
 *  - When a ignored player send a trade request to a player who has ignored him, should he still show a waiting GUI ?
 *    * It's to handicap players who spam or have undesired behaviour, but some people can be ignored for random reasons.
 *    * For FairTrade v3.1, it's not possible to have the behaviour: show waiting GUI BUT prevent chat message to notify
 *     (a ignore would just prevent the message to tell to accept or refuse). So it's simpler and also looks like more
 *     accurate to notify ignored player that he's ignored.
 *  ==> NO
 *
 *  Storage of data
 *  ---------------
 *
 *  pluginDataFolder/lists/<player UUID>.yml
 *
 *  <player UUID> is the UUID of the player ignoring the players the file contains.
 *  Each player also own an unique .yml, file that is a list of UUID of players they are ignoring.
 *
 *  Commands
 *  --------
 *
 *  Inspired by Hypixel ignore system [https://hypixel.fandom.com/wiki/Ignore_System]
 *
 *   - /tradeignore add <player>
 *   - /tradeignore remove <player>
 *   - /tradeignore list <player(optional, default same player)>
 *
 *  Permissions
 *  -----------
 *
 *  fairtrade.ignore.listany: Show any player ignore list. By default each player can only see his own ignorelist.
 */

public final class FairTradeIgnore extends JavaPlugin implements Listener {

    public static abstract class BaseCommand implements CommandExecutor {
        protected final FairTradeIgnore plugin;

        public BaseCommand(FairTradeIgnore plugin) {
            this.plugin = plugin;
        }

        /**
         * Executes the given command, returning its success.
         * <br>
         * If false is returned, then the "usage" plugin.yml entry for this command
         * (if defined) will be sent to the player.
         *
         * @param sender  Source of the command
         * @param command Command which was executed
         * @param label   Alias of the command which was used
         * @param args    Passed command arguments
         * @return true if a valid command, otherwise false
         */
        @Override
        public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
    }

    private FairTrade plugin;
    private final Map<Player, IgnoreList> ignoreLists = new HashMap<>();
    private Messages messages;
    private final Yaml yaml;
    private String missingPermissionMessage;

    public FairTradeIgnore() {
        DumperOptions opts = new DumperOptions();
        opts.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(opts);
    }

    public Messages getMessages() {
        return messages;
    }

    public FairTrade getParent() {
        return plugin;
    }

    public String getMissingPermissionMessage() {
        return missingPermissionMessage;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = (FairTrade)Bukkit.getPluginManager().getPlugin("FairTrade");
        messages = new Messages(this);
        Bukkit.getPluginManager().registerEvents(this, this);

        PluginCommand command = Objects.requireNonNull(getCommand("tradeignore"));
        command.setExecutor(new MainCommand(this));
        command.setTabCompleter(new MainCompleter());
        missingPermissionMessage = command.getPermissionMessage();

        if(missingPermissionMessage == null) {
            missingPermissionMessage = "You have not the permission to use this command.";
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onAsk(AskForTransaction e) {
        if(getIgnoreListOf(e.replier).contains(e.initiator)) {
            e.setCancelled(true);
            e.setCancellationReason(messages.ignored.translate(e.replier));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        ignoreLists.remove(e.getPlayer());
    }

    public Yaml getYaml() {
        return yaml;
    }

    public IgnoreList getIgnoreListOf(Player player) {
        return ignoreLists.computeIfAbsent(player, this::createIgnoreList);
    }

    private IgnoreList createIgnoreList(Player player) {
        return new IgnoreList(this, player);
    }
}
