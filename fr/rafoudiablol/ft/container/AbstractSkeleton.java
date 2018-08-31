package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.main.FairTrade;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSkeleton {

    private AbstractSlotType[] matrix; // multiple of 9

    public AbstractSkeleton(AbstractSlotType[] m) {
        matrix = m;

        if(m.length % 9 != 0) {
            FairTrade.getFt().w("AbstractSkeleton(): " + m.length + " is not multiple of 9");
        }
    }

    /**
     *
     * @param slot
     * @return le combien-tième slot de ce type est-il dans l'inventaire
     */
    public int indexOf(int slot) {

        Class<? extends AbstractSlotType> clazz = matrix[slot].getClass();
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
    public int nth(Class<? extends AbstractSlotType> clazz, int n) {

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
     * @return une List de tous les slots qui sont du type clazz, triés par ordre croissant
     */
    public List<Integer> byType(Class<? extends AbstractSlotType> clazz) {

        List<Integer> ret = new LinkedList<>();
        for(int i = 0; i < matrix.length; ++i) {
            if(clazz.equals(matrix[i].getClass())) {
                ret.add(i);
            }
        }
        return ret;
    }
}
