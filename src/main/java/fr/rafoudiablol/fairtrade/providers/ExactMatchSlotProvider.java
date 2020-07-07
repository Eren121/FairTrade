package fr.rafoudiablol.fairtrade.providers;

import fr.rafoudiablol.fairtrade.layout.SlotProvider;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionSlot;
import org.jetbrains.annotations.Nullable;

public class ExactMatchSlotProvider implements SlotProvider {
    public interface Constructor {
        TransactionSlot create(TransactionScreen screen, int rawSlot);
    }

    private final String identifier;
    private final Constructor constructor;

    public ExactMatchSlotProvider(String layoutIdentifier, Constructor constructor) {
        this.identifier = layoutIdentifier;
        this.constructor = constructor;
    }

    @Override
    public @Nullable TransactionSlot createSlot(String identifier, TransactionScreen screen, int rawSlot) {
        if(identifier.equals(this.identifier)) {
            return constructor.create(screen, rawSlot);
        }

        return null;
    }
}
