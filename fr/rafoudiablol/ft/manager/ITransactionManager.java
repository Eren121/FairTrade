package fr.rafoudiablol.ft.manager;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface ITransactionManager {

    PlayerStatus getStatus(UUID uuid);
    Player popSource(Player dest);
}
