package fr.rafoudiablol.screen;

import org.bukkit.event.inventory.InventoryAction;

public class ActionHelper {

    /**
     * @return true if the action can only at maximum affect the slot where is the cursor and/or the item in the cursor.
     *         else false in all other cases
     *  Useful to check if the action is safe to use for GUI slots that should not be modified,
     *  and easely accept it without more checking.
     *
     *  Filter quickly the simplest actions to keep the code checking the most complex event to know if they
     *  are forbidden or not on a case by case basis.
     */
    public boolean concernOnlyCurrentSlot(InventoryAction action) {
        switch(action) {
            case NOTHING:
            case SWAP_WITH_CURSOR:
            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_SOME:
            case PICKUP_ONE:
            case PLACE_ALL:
            case PLACE_SOME:
            case PLACE_ONE:
            case DROP_ONE_SLOT:
            case DROP_ALL_SLOT:
            case DROP_ONE_CURSOR:
            case DROP_ALL_CURSOR:
            case CLONE_STACK:
                return true;

            case MOVE_TO_OTHER_INVENTORY:
            case COLLECT_TO_CURSOR:
            case HOTBAR_SWAP: // Can also affect hotbar, should be further check because custom GUI is on top
            case HOTBAR_MOVE_AND_READD: // almost same has hotbar swap. From test, fired when swap from another inventory of the same view
            case UNKNOWN:
            default:
                return false;
        }
    }

    /**
     * Some event do not concern any slot (special or invalid actions).
     * Use primarly to avoid IndexOutOfBoundsException, because in these case the slot may be invalid, or even negative,
     * like -1 (or -999).
     */
    public boolean hasSlot(InventoryAction action) {
        switch(action) {
            case NOTHING:
            case UNKNOWN:
            case DROP_ALL_CURSOR:
            case DROP_ONE_CURSOR:
                return false;

            default:
                return true;
        }
    }
}
