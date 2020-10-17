package com.jlbcontrols.ftct.tagconfigmodifiers;


import com.inductiveautomation.ignition.common.config.BoundValue;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.types.OpcTagTypeProperties;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.jlbcontrols.ftct.TagUtils;

import java.util.Map;

public class OpcPathModifier extends AbstractTagConfigItemModifier {

    private final Map<String, TagPath> prefixFolderMap;
    private final String opcStartString;

    public OpcPathModifier(Map<String, TagPath> prefixFolderMap, String opcStartString) {
        this.prefixFolderMap = prefixFolderMap;
        this.opcStartString = opcStartString;
    }

    @Override
    public TagConfiguration modify(TagConfiguration tagConfig) {
        String oldOpcPath = TagUtils.getOpcPathString(tagConfig);
        if(oldOpcPath!=null){
            for(String prefix : prefixFolderMap.keySet()){
                if(oldOpcPath.contains(prefix)){
                    tagConfig.setBoundValue(OpcTagTypeProperties.OPCItemPath, new BoundValue("parameter", opcStartString+prefix+"{TagName}"));
                }
            }
        }
        return tagConfig;
    }
}
