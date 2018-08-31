package fr.rafoudiablol.ft.utils.inv;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class Builder {

    //EnumI18n.TITLE.localize(left.getDisplayName(), right.getDisplayName())

    public static Inventory buildInventory(AbstractSkeleton sk, String title) {

        Inventory inv = Bukkit.createInventory(new Holder(sk), sk.length(), title);

        for(int i = 0; i < sk.length(); ++i) {
            inv.setItem(i, sk.type(i).getDefault());
        }

        return inv;
    }
}
