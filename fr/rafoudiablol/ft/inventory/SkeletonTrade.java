package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.AbstractSlotType;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class SkeletonTrade extends AbstractSkeleton {

    public SkeletonTrade(AbstractSlotType[] m) {
        super(m);
    }

    @Override
    public void close(HumanEntity src) {

        PlayerStatus status = FairTrade.getFt().getManager().getStatus(src.getUniqueId());

        // Dunno
        if(status != null && !status.aborted) {

            Player player = status.getPlayer();
            Player other = status.getOther();
            PlayerStatus otherStatus = FairTrade.getFt().getManager().getStatus(other.getUniqueId());
            status.aborted = true;

            if (otherStatus != null) {
                otherStatus.aborted = true;
                other.closeInventory();
                FairTrade.getFt().taskAtNextTick(other::closeInventory);
            }

            Bukkit.getPluginManager().callEvent(new AbortTransactionEvent(player, other));
        }
    }
}
