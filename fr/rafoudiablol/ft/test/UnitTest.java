package fr.rafoudiablol.ft.test;

import fr.rafoudiablol.ft.inventory.SlotConfirm;
import fr.rafoudiablol.ft.inventory.SlotLocal;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.utils.ArraysUtils;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import org.apache.commons.lang.Validate;

@SuppressWarnings("HardCodedStringLiteral")
public class UnitTest
{
    public void checkSkeletonInventory()
    {
        AbstractSkeleton sk = FairTrade.getFt().getOptions().getSkeleton();

        Validate.isTrue(sk.byType(SlotConfirm.class).size() > 0, "confirm button not found");
        Validate.isTrue(sk.byType(SlotConfirm.class).size() < 2, "too many confirm buttons");
        Validate.isTrue(sk.byType(SlotLocal.class).size() == sk.byType(SlotRemote.class).size(), "count not match");
    }

    public void checkArraysUtils() {

        Integer[] a1 = {1, 2, null, 3, null, 4, null, null};
        Validate.isTrue(ArraysUtils.removeNullFromArray(a1).length == 4);
    }
}
