package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Holder implements InventoryHolder {

    private final AbstractSkeleton skeleton;
    private boolean aborted;

    public Holder(AbstractSkeleton skeleton) {
        this.skeleton = skeleton;
        this.aborted = false;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public boolean isAborted() {
        return aborted;
    }

    public void stopTracing() {
        aborted = true;
    }

    /**
     * Does check if yet tracing
     */
    public static AbstractSkeleton tryGet(InventoryHolder holder) {
        return holder instanceof Holder && !((Holder)holder).isAborted() ? ((Holder) holder).skeleton : null;
    }

    /**
     * Does NOT check if yet tracing
     */
    public static boolean isInstanceof(Inventory inv, Class<? extends AbstractSkeleton> clazz) {
        AbstractSkeleton sk = inv.getHolder() instanceof Holder ? ((Holder) inv.getHolder()).skeleton : null;
        return sk != null && clazz.equals(sk.getClass());
    }
}
