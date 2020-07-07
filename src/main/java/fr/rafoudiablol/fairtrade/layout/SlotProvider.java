package fr.rafoudiablol.fairtrade.layout;

import fr.rafoudiablol.fairtrade.screens.transaction.TransactionScreen;
import fr.rafoudiablol.fairtrade.screens.transaction.TransactionSlot;
import org.jetbrains.annotations.Nullable;

public interface SlotProvider {
    @Nullable TransactionSlot createSlot(String identifier, TransactionScreen screen, int rawSlot);
}
