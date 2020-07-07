package fr.rafoudiablol.fairtrade.providers;

import fr.rafoudiablol.fairtrade.screens.transaction.ResourceSlot;
import fr.rafoudiablol.fairtrade.transaction.TradeResource;
import fr.rafoudiablol.fairtrade.slots.EmptySlot;

public class ResourceSlotProvider extends ExactMatchSlotProvider {

    public ResourceSlotProvider(String layoutIdentifier, final String resource) {
        super(layoutIdentifier, (screen, rawSlot) -> {
            final TradeResource tradeResource = screen.getPlugin().transactionManager.getResource(resource);
            if(tradeResource == null) {
                return new EmptySlot(screen, rawSlot);
            }
            else {
                return new ResourceSlot(screen, rawSlot, tradeResource) {
                    @Override
                    public String getSkinName() {
                        return resource;
                    }
                };
            }
        });
    }
}
