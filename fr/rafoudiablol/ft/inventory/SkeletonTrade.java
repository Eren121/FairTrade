package fr.rafoudiablol.ft.inventory;

import fr.rafoudiablol.ft.events.AbortTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.manager.PlayerStatus;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import fr.rafoudiablol.ft.utils.inv.Holder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class SkeletonTrade extends AbstractSkeleton {

    public SkeletonTrade(int[] slots) {

        registerSlot(0, new SlotEmpty());
        registerSlot(1, new SlotOwner());
        registerSlot(2, new SlotRemote());
        registerSlot(3, new SlotConfirm());
        registerSlot(7, new SlotStatusLocal());
        registerSlot(8, new SlotStatusRemote());
        setMatrix(slots);
    }

    /**
     * minimalist default constructor, used if options are broken
     */
    public SkeletonTrade() {
       this(new int[] {1, 1, 1, 7, 3, 8, 2, 2, 2});
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

                ((Holder)other.getOpenInventory().getTopInventory().getHolder()).stopTracing();
                otherStatus.aborted = true;
                other.closeInventory();
                FairTrade.getFt().taskAtNextTick(other::closeInventory);
            }

            Bukkit.getPluginManager().callEvent(new AbortTransactionEvent(player, other));
        }
    }
}
