package fr.rafoudiablol.ft.config;

import org.bukkit.inventory.ItemStack;

public interface IOptions {

    double getDistanceMax();
    void setDistanceMax(double f);

    I18n geti18n();
    ItemStack getDummyItem(boolean ok);

    default double getDistanceMinSq() {
        double f = getDistanceMax();
        return f * f;
    }

    int[] getSkeSlots();
}
