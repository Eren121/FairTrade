package fr.rafoudiablol.ft.config;

import fr.rafoudiablol.ft.inventory.SkeletonTrade;
import fr.rafoudiablol.ft.spy.SkeletonLog;
import org.bukkit.inventory.ItemStack;

public interface IOptions {

    double getDistanceMax();
    void setDistanceMax(double f);

    I18n geti18n();
    ItemStack getDummyItem(boolean ok);
    boolean canSelfTrade();

    default double getDistanceMaxSq() {
        double f = getDistanceMax();
        return f * f;
    }

    ItemStack getEmptyItem();
    SkeletonTrade getSkeleton();
    SkeletonLog getSkeletonForLog();

    ItemStack getDefaultItem(EnumEditableItems item);
}
