package com.jlbcontrols.ftct.tagfilters;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.types.TagObjectType;

public class TagObjectTypeFilter extends AbstractTagConfigItemFilter {

    private final TagObjectType tagObjectType;

    public TagObjectTypeFilter(TagObjectType tagObjectType) {
        this.tagObjectType = tagObjectType;
    }

    @Override
    public boolean pass(TagConfiguration tagConfig) {
        return tagConfig.getType() == tagObjectType;
    }
}
