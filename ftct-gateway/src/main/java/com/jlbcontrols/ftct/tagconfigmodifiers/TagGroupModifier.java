package com.jlbcontrols.ftct.tagconfigmodifiers;


import com.inductiveautomation.ignition.common.config.BoundValue;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.properties.WellKnownTagProps;

public class TagGroupModifier extends AbstractTagConfigItemModifier {

    private final String tagGroupString;

    public TagGroupModifier(String tagGroupString) {
        this.tagGroupString = tagGroupString;
    }

    @Override
    public TagConfiguration modify(TagConfiguration tagConfig) {
        tagConfig.setBoundValue(WellKnownTagProps.TagGroup, new BoundValue("parameter", tagGroupString));
        return tagConfig;
    }
}
