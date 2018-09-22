package fr.rafoudiablol.ft.utils.inv;

import fr.rafoudiablol.ft.main.FairTrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractSkeleton {

    private AbstractSlot[] matrix; // multiple of 9
    private Map<Integer, AbstractSlot> slots = new HashMap<>();

    public void registerSlot(AbstractSlot type) {
        slots.put(type.getId(), type);
        FairTrade.getFt().i(getClass().getName() + " bind id " + type.getId() + " " + type.getClass().getName());
    }

    public void setMatrix(int[] ids) {

        if(ids.length % 9 != 0) {
            FairTrade.getFt().w("AbstractSkeleton(): " + ids.length + " is not multiple of 9");
        }

        matrix = new AbstractSlot[ids.length];
        for(int i = 0; i < matrix.length; ++i) {
            matrix[i] = slots.get(ids[i]);

            if(matrix[i] == null) {
                FairTrade.getFt().w(getClass() + ": id not bind " + ids[i]);
            }
        }
    }

    /**
     *
     * @param slot
     * @return le combien-tième slot de ce type est-il dans l'inventaire
     */
    public int nth(int slot) {

        Class<? extends AbstractSlot> clazz = matrix[slot].getClass();
        int ret = 1;
        for(int i = 0; i < slot; ++i) {
            if(matrix[i].getClass().equals(clazz)) {
                ++ret;
            }
        }
        return ret;
    }

    /**
     * @param clazz slot type class
     * @param n the nth slot to get
     * @return le slot qui contient le n-ième slot du type représenté par clazz
     */
    public int nth(Class<? extends AbstractSlot> clazz, int n) {

        for(int i = 0; i < matrix.length; ++i) {
            if(clazz.equals(matrix[i].getClass())) {
                n--;
            }
            if(n == 0) {
                return i;
            }
        }

        FairTrade.getFt().w("AbstractSkeleton.nth(): not found index " + n + " of type " + clazz);
        return -1;
    }

    /**
     * @return a list of all slots that meet the condition slotType.getClass() == clazz, sorted ascending order
     */
    public List<Integer> byType(Class<? extends AbstractSlot> clazz) {

        List<Integer> ret = new LinkedList<>();
        for(int i = 0; i < matrix.length; ++i) {
            if(clazz.equals(matrix[i].getClass())) {
                ret.add(i);
            }
        }
        return ret;
    }

    /**
     *
     * @param clazz
     * @return the first slot of this type. -1 if no slot found.
     */
    public int firstSlot(Class<? extends AbstractSlot> clazz) {

        for(int i = 0; i < size(); ++i) {
            if(clazz.equals(matrix[i].getClass())) {
                return i;
            }
        }

        return -1;
    }

    public AbstractSlot get(int slot) {
        return matrix[slot];
    }

    public int size() {
        return matrix.length;
    }

    public Inventory buildInventory(String title) {

        Inventory inv = Bukkit.createInventory(new Holder(this), matrix.length, title);

        for(int i = 0; i < matrix.length; ++i) {
            inv.setItem(i, matrix[i].getDefault());
        }

        return inv;
    }

    public void close(HumanEntity player)
    {
    }

    public boolean action(InventoryClickEvent e) {
        return matrix[e.getSlot()].action(e);
    }
}
