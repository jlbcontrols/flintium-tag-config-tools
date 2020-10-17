package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.tags.config.CollisionPolicy;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.jlbcontrols.ftct.tagconfigmodifiers.TagConfigItemModifier;
import com.jlbcontrols.ftct.tagfilters.TagConfigItemFilter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FilteredUpdater {

    TagPath parentPath;
    TagProvider provider;
    TagConfigItemFilter filter;
    TagConfigItemModifier modifier;

    public FilteredUpdater(TagPath parentPath, TagProvider provider, TagConfigItemFilter filter, TagConfigItemModifier modifier) {
        this.parentPath = parentPath;
        this.provider = provider;
        this.filter = filter;
        this.modifier = modifier;
    }

    public List<TagConfiguration> getAllConfigs() throws Exception {
        List<TagPath> tagPaths = TagUtils.findPaths(provider,parentPath,true,false);
        List<TagConfiguration> tagConfigs = TagUtils.getTagConfigs(provider, tagPaths);
        return tagConfigs;
    }

    public int updateTagConfigs() throws Exception {
        List<TagConfiguration> allConfigs = getAllConfigs();
        List<TagConfiguration> filteredConfigs = filter.filterConfigs(allConfigs);
        List<TagConfiguration> newConfigs = modifier.modifyConfigs(filteredConfigs);
        List<QualityCode> results = provider.saveTagConfigsAsync(newConfigs, CollisionPolicy.MergeOverwrite).get(30, TimeUnit.SECONDS);

        for (int i = 0; i < results.size(); i++) {
            QualityCode result = results.get(i);
            if (result.isNotGood()) {
                throw new Exception(String.format("Edit tag operation returned bad result '%s'", result.toString()));
            }
        }
        return newConfigs.size();
    }
}
