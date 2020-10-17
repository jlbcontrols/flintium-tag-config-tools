package com.jlbcontrols.ftct.tagfilters;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.properties.WellKnownTagProps;

public class HistoryEnabledFilter extends AbstractTagConfigItemFilter {

    @Override
    public boolean pass(TagConfiguration tagConfig) {
        Boolean historyEnabled = tagConfig.get(WellKnownTagProps.HistoryEnabled);
        return (historyEnabled != null && historyEnabled);
    }
}
