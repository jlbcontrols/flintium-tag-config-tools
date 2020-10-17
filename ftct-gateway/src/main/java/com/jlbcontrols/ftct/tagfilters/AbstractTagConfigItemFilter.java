package com.jlbcontrols.ftct.tagfilters;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTagConfigItemFilter implements TagConfigItemFilter {

    @Override
    abstract public boolean pass(TagConfiguration tagConfig);

    @Override
    public List<TagConfiguration> filterConfigs(List<TagConfiguration> unfilteredTagConfigs) {
        List<TagConfiguration> filteredTagConfigs = new ArrayList<>();
        for (TagConfiguration tagConfig : unfilteredTagConfigs){
            if(this.pass(tagConfig)){
                filteredTagConfigs.add(tagConfig);
            }
        }
        return filteredTagConfigs;
    }
}
