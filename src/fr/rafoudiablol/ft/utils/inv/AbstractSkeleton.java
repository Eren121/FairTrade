package fr.rafoudiablol.ft.utils.inv;

import fr.rafoudiablol.ft.main.FairTrade;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractSkeleton {

    private ISlot[] matrix; // multiple of 9
    private Map<Integer, ISlot> slots = new HashMap<>();

    public void registerSlot(int id, ISlot type) {
        slots.put(id, type);
        FairTrade.getFt().i(getClass().getName() + " bind item " + id + " " + type.getClass().getName());
    }

    public void setMatrix(int[] ids) {

        if(ids.length % 9 != 0) {
            FairTrade.getFt().w("AbstractSkeleton(): " + ids.length + " is not multiple of 9");
        }

        matrix = new ISlot[ids.length];
        for(int i = 0; i < matrix.length; ++i) {
            matrix[i] = slots.get(ids[i]);

            if(matrix[i] == null) {
                FairTrade.getFt().w(getClass() + ": item not bind " + ids[i]);
            }
        }
    }

    /**
     *
     * @param slot
     * @return le combien-tième slot de ce type est-il dans l'inventaire
     */
    public int nth(int slot) {

        Class<? extends Object> clazz = matrix[slot].getClass();
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
    public int nth(Class<? extends Object> clazz, int n) {

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
    public List<Integer> byType(Class<? extends ISlot> clazz) {

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
    public int firstSlot(Class<? extends ISlot> clazz) {

        for(int i = 0; i < size(); ++i) {
            if(clazz.equals(matrix[i].getClass())) {
                return i;
            }
        }

        return -1;
    }

    public ISlot get(int slot) {
        return matrix[slot];
    }

    public int size() {
        return matrix.length;
    }

    public Inventory buildInventory(String title) {

        return buildInventory(title, new Holder(this));
    }

    public Inventory buildInventory(String title, InventoryHolder customInventoryHolder) {

        Inventory inv = Bukkit.createInventory(customInventoryHolder, matrix.length, title);

        for(int i = 0; i < matrix.length; ++i) {
            inv.setItem(i, matrix[i].getDefault(i));
        }

        return inv;
    }

    public void close(HumanEntity player)
    {
    }

    public boolean action(InventoryClickEvent e) {
        return matrix[e.getSlot()].action(e);
    }

    public ISlot getRegisteredSlot(int i) {
        return slots.get(i);
    }
}
