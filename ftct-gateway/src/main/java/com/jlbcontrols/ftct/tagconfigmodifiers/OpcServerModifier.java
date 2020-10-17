package com.jlbcontrols.ftct.tagconfigmodifiers;

import com.inductiveautomation.ignition.common.config.BoundValue;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.types.OpcTagTypeProperties;

public class OpcServerModifier extends AbstractTagConfigItemModifier {

    public OpcServerModifier(String opcServerString) {
        this.opcServerString = opcServerString;
    }

    String opcServerString;

    @Override
    public TagConfiguration modify(TagConfiguration tagConfig) {
        tagConfig.setBoundValue(OpcTagTypeProperties.OPCServer, new BoundValue("parameter", opcServerString));
        return tagConfig;
    }
}
