package com.jlbcontrols.ftct.tagconfigmodifiers;


import com.inductiveautomation.ignition.common.config.BoundValue;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.properties.WellKnownTagProps;

public class HistoryProviderModifier extends AbstractTagConfigItemModifier {

    private final String historyProviderString;

    public HistoryProviderModifier(String historyProviderString) {
        this.historyProviderString = historyProviderString;
    }

    @Override
    public TagConfiguration modify(TagConfiguration tagConfig) {
        tagConfig.setBoundValue(WellKnownTagProps.HistoryProvider, new BoundValue("parameter", historyProviderString));
        return tagConfig;
    }
}
