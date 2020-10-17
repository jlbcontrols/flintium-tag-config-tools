package com.jlbcontrols.ftct.tagfilters;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.jlbcontrols.ftct.TagUtils;

public class OpcPathContainsFilter extends AbstractTagConfigItemFilter {

    private final String containedString;

    public OpcPathContainsFilter(String containedString) {
        this.containedString = containedString;
    }

    @Override
    public boolean pass(TagConfiguration tagConfig) {
        OpcFilter opcFilter = new OpcFilter();
        if(opcFilter.pass(tagConfig)){
            String oldOpcPath = TagUtils.getOpcPathString(tagConfig);
            if(oldOpcPath!=null){
                return oldOpcPath.contains(containedString);
            }
        }
        return false;
    }
}
