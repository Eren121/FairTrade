package fr.rafoudiablol.ft.manager;

import org.bukkit.entity.Player;

public class PlayerStatus implements ITransactionLink {

    private Player player, other;
    private boolean confirm = false;
    private boolean ask;
    public boolean aborted = false;
    public int xp;

    public PlayerStatus(Player p1, Player p2, boolean flag) {
        player = p1;
        other = p2;
        ask = flag;
    }

    public boolean hasConfirm() {
        return confirm;
    }

    public void setConfirm(boolean flag) {
        confirm = flag;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Player getOther() {
        return other;
    }

    public boolean isAsker() {
        return ask;
    }
}
