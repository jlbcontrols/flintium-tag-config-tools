package com.jlbcontrols.ftct.tagconfigmodifiers;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTagConfigItemModifier implements TagConfigItemModifier{

    @Override
    public abstract TagConfiguration modify(TagConfiguration oldConfig);

    @Override
    public List<TagConfiguration> modifyConfigs(List<TagConfiguration> unmodifiedTagConfigs) {
        List<TagConfiguration> modifiedTagConfigs = new ArrayList<>();
        for (TagConfiguration tagConfig : unmodifiedTagConfigs){
            modifiedTagConfigs.add(modify(tagConfig));
        }
        return modifiedTagConfigs;
    }
}
