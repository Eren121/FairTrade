package fr.rafoudiablol.fairtrade.providers;

import fr.rafoudiablol.fairtrade.layout.SlotProvider;
import fr.rafoudiablol.fairtrade.transaction.Side;
import fr.rafoudiablol.fairtrade.slots.ItemSlot;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionSlot;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemSlotProvider implements SlotProvider {
    private Pattern pattern;
    private final Side side;

    public ItemSlotProvider(String prefix, Side side) {
        this.pattern = Pattern.compile(Pattern.quote(prefix) + "([0-9]+)");
        this.side = side;
    }

    @Override
    public @Nullable TransactionSlot createSlot(String identifier, TransactionScreen screen, int rawSlot) {
        final Matcher matcher = pattern.matcher(identifier);
        if(matcher.matches()) {
            final int index = Integer.parseInt(matcher.group(1));
            return new ItemSlot(screen, rawSlot, side, index);
        }

        return null;
    }
}
