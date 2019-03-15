package fr.rafoudiablol.ft.editor;

import fr.rafoudiablol.ft.config.EnumEditableItems;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;

public class SkeletonLayout extends AbstractSkeleton {

    public SkeletonLayout(AbstractSkeleton skeleton) {

        int size = skeleton.size();
        int[] slots = new int[size + 9];
        registerSlot(0, new SlotLayout(skeleton));

        for(EnumEditableItems item : EnumEditableItems.values()) {
            registerSlot(1 + item.ordinal(), new SlotItem(skeleton, item));
        }

        setMatrix(slots);
    }
}
