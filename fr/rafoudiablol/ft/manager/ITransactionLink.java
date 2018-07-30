package fr.rafoudiablol.ft.manager;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ITransactionLink {

    Player getPlayer();
    Player getOther();

    default UUID getPlayerID() {
        return getPlayer().getUniqueId();
    }

    default UUID getOtherID() {
        return getOther().getUniqueId();
    }

    default void forEach(BiConsumer<Player, Player> lambda) {

        lambda.accept(getPlayer(), getOther());
        lambda.accept(getOther(), getPlayer());
    }

    default void forEach(Consumer<Player> lambda) {

        lambda.accept(getPlayer());
        lambda.accept(getOther());
    }
}
