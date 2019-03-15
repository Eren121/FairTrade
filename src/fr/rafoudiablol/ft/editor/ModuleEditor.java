package fr.rafoudiablol.ft.editor;

import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.main.IFairTradeModule;

public class ModuleEditor implements IFairTradeModule {
    private SkeletonLayout skeletonLayout;
    private CommandEdit commandEdit;

    @Override
    public void onEnableModule(FairTrade fairtrade) {

        skeletonLayout = new SkeletonLayout(fairtrade.getOptions().getSkeleton());
        commandEdit = new CommandEdit(skeletonLayout);

        fairtrade.getCommand(commandEdit.getName()).setExecutor(commandEdit);
    }

    @Override
    public void onDisableModule(FairTrade fairtrade) {

    }
}
