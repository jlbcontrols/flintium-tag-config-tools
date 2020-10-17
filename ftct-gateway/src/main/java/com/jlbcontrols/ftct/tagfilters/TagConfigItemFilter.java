package com.jlbcontrols.ftct.tagfilters;


import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;

import java.util.List;

public interface TagConfigItemFilter {

    boolean pass(TagConfiguration tagConfig);

    List<TagConfiguration> filterConfigs(List<TagConfiguration> unfilteredTagConfigs);
}
