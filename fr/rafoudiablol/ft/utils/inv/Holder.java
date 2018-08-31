package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

class Holder implements InventoryHolder {

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

    public void abort() {
        aborted = true;
    }

    public static AbstractSkeleton tryGet(InventoryHolder holder) {
        return holder instanceof Holder && !((Holder)holder).isAborted() ? ((Holder)holder).skeleton : null;
    }
}
