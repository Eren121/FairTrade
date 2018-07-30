package fr.rafoudiablol.ft.container;

import com.google.common.collect.Lists;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class Skeleton
{
    private static final Map<Integer, Integer> mirrorLocations = new HashMap<>();
    private static final Map<Locations, List<Integer>> slots = new HashMap<>();

    // length multiple of LINE_LENGTH
    // have to be mirrored horizontally
    public static final int[] INVENTORY = {
            1, 1, 1, 7, 8, 8, 2, 2, 2,
            1, 1, 1, 7, 3, 8, 2, 2, 2,
            1, 1, 1, 7, 7, 8, 2, 2, 2
    };

    public static int getMirrorLocation(int loc) {
        return mirrorLocations.get(loc);
    }
    public static int getConfirm() {
        return slots.get(Locations.Confirm).get(0);
    }
    public static List<Integer> getOwners() {
        return slots.get(Locations.Owner);
    }
    public static List<Integer> getEmpties() {
        return slots.get(Locations.Empty);
    }
    public static List<Integer> getOwnerLeds() { return slots.get(Locations.OwnerConfirm); }
    public static List<Integer> getRemoteLeds() { return slots.get(Locations.RemoteConfirm); }
    public static ItemStack[] getOwnerItems(Inventory inv) { return bind(Locations.Owner, inv); }

    public static ItemStack[] bind(Locations loc, Inventory inv) {
        return Lists.transform(slots.get(loc), inv::getItem).toArray(new ItemStack[0]);
    }

    public static List<Integer> getSlots(Locations loc) {
        return slots.get(loc);
    }

    static
    {
        Arrays.stream(Locations.values()).forEach(loc -> slots.put(loc, new ArrayList<>()));

        int j = 0;
        for(int i = 0; i < INVENTORY.length; ++i)
        {
            if(INVENTORY[i] == Locations.Owner.id)
            {
                while(INVENTORY[j] != Locations.Remote.id) {
                    j++;
                }

                mirrorLocations.put(i, j);
                mirrorLocations.put(j, i);
                j++;
            }

            slots.get(Locations.valueOf(INVENTORY[i])).add(i);
        }
    }
}