package com.jlbcontrols.ftct.tagfilters;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.properties.WellKnownTagProps;
import com.inductiveautomation.ignition.common.tags.config.types.OpcTagTypeProperties;

public class OpcFilter extends AbstractTagConfigItemFilter {

    @Override
    public boolean pass(TagConfiguration tagConfig) {
        String valueSource = tagConfig.get(WellKnownTagProps.ValueSource);
        return valueSource != null && valueSource.equalsIgnoreCase(OpcTagTypeProperties.TAG_TYPE);
    }
}
