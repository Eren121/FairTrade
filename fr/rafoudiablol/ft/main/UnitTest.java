package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.inventory.SlotConfirm;
import fr.rafoudiablol.ft.inventory.SlotOwner;
import fr.rafoudiablol.ft.inventory.SlotRemote;
import fr.rafoudiablol.ft.utils.Arrais;
import fr.rafoudiablol.ft.utils.inv.AbstractSkeleton;
import org.apache.commons.lang.Validate;

@SuppressWarnings("HardCodedStringLiteral")
public class UnitTest
{
    public void checkSkeletonInventory()
    {
        AbstractSkeleton sk = FairTrade.getFt().getOptions().getSkeleton();

        Validate.isTrue(sk.byType(SlotConfirm.class).size() > 0, "confirm button not found");
        Validate.isTrue(sk.byType(SlotConfirm.class).size() < 1, "too many confirm buttons");
        Validate.isTrue(sk.byType(SlotOwner.class).size() == sk.byType(SlotRemote.class).size(), "count not match");
    }

    public void checkArrais() {

        Integer[] a1 = {1, 2, null, 3, null, 4, null, null};
        Validate.isTrue(Arrais.removeNullFromArray(a1).length == 4);
    }
}
