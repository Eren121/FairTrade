package fr.rafoudiablol.ft.main;

import fr.rafoudiablol.ft.container.Locations;
import fr.rafoudiablol.ft.utils.Arrais;
import org.apache.commons.lang.Validate;

import static fr.rafoudiablol.ft.container.Skeleton.*;
import static java.util.Arrays.stream;

@SuppressWarnings("HardCodedStringLiteral")
public class UnitTest
{
    public void checkSkeletonInventory()
    {
        Validate.isTrue(getMirrorLocation(2) == 8, getMirrorLocation(2) + "");
        Validate.isTrue(getMirrorLocation(10) == 16, getMirrorLocation(10) + "");

        int[] array = INVENTORY;
        boolean confirmFound = false;

        for(int i = 0; i < array.length; ++i)
        {
            if(array[i] == Locations.Owner.id && array[getMirrorLocation(i)] != Locations.Remote.id)
            {
                throw new RuntimeException("test: " + i);
            }
            else if(array[i] == Locations.Remote.id && array[getMirrorLocation(i)] != Locations.Owner.id)
            {
                throw new RuntimeException("test: " + i);
            }
            else if(array[i] == Locations.Confirm.id)
            {
                if(confirmFound)
                {
                    throw new RuntimeException("test: " + i);
                }

                confirmFound = true;
            }
        }

        //noinspection HardCodedStringLiteral
        Validate.isTrue(confirmFound, "confirm button not found");
        Validate.isTrue(stream(array).filter(i -> i == Locations.Owner.id).count() == stream(array).filter(i -> i == Locations.Remote.id).count(),
           "count not match");
    }

    public void checkArrais() {

        Integer[] a1 = {1, 2, null, 3, null, 4, null, null};
        Validate.isTrue(Arrais.removeNullFromArray(a1).length == 4);
    }
}
