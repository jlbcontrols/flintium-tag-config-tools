package com.jlbcontrols.ftct.tagconfigmodifiers;

import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;

import java.util.List;

public interface TagConfigItemModifier {

    TagConfiguration modify(TagConfiguration oldConfig);

    List<TagConfiguration> modifyConfigs(List<TagConfiguration> unmodifiedTagConfigs);
}
