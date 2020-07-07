package fr.rafoudiablol.fairtrade.screens.transaction;

import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.fairtrade.slots.ItemSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Quickly discover where is mirror slot (from a local item slot index X, where is remote slot index X and
 * reciprocally).
 */
public class MirrorSlotsCache {
    static class Pair {
        private final Map<Side, ItemSlot> sides = new HashMap<>();

        public @NotNull ItemSlot getMirror(ItemSlot slot) {
            return Objects.requireNonNull(get(slot.getSide().opposite()));
        }

        public boolean isComplete() {
            return sides.size() == Side.length;
        }

        public boolean isMissing(Side side) {
            return sides.get(side) == null;
        }

        public @Nullable ItemSlot get(Side side) {
            return sides.get(side);
        }

        void putSlot(@NotNull ItemSlot slot) {
            sides.put(slot.getSide(), slot);
        }
    }

    protected final Map<Integer, Pair> itemSlotPairs = new HashMap<>();

    public void update(TransactionScreen screen) {
        itemSlotPairs.clear();

        for(int rawSlot = 0; rawSlot < screen.length(); rawSlot++) {
            if(screen.getSlot(rawSlot) instanceof ItemSlot) {
                final ItemSlot itemSlot = (ItemSlot)screen.getSlot(rawSlot);
                Pair pair = itemSlotPairs.get(itemSlot.getIndex());

                if(pair == null) {
                    pair = new Pair();
                    pair.putSlot(itemSlot);
                    itemSlotPairs.put(itemSlot.getIndex(), pair);
                }
                else if(pair.get(itemSlot.getSide()) != null) {
                    screen.getPlugin().getLogger().severe("duplicate slot (index " + rawSlot + ") in layout, ignored, this slot will not work");
                }
                else {
                    pair.putSlot(itemSlot);
                }
            }
        }

        checkEverySlotHasPair(screen);
    }

    public void checkEverySlotHasPair(TransactionScreen screen) {
        for(Map.Entry<Integer, Pair> entry : itemSlotPairs.entrySet()) {
            if(!entry.getValue().isComplete()) {

                for(Side side : Side.values()) {
                    if(entry.getValue().isMissing(side)) {
                        screen.getPlugin().getLogger().severe("Item slot n°" + entry.getKey() + " has no equivalent in " + side + " slot! the plugin may throw exception, crash or have any undefined behaviour. Please fix your config file.");
                    }
                }
            }
        }
    }

    public @NotNull ItemSlot getMirror(ItemSlot slot) {
        return Objects.requireNonNull(itemSlotPairs.get(slot.getIndex())).getMirror(slot);
    }
}
