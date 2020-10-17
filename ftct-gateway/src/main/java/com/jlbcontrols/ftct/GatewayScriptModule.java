package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.inductiveautomation.ignition.common.tags.paths.parser.TagPathParser;
import com.jlbcontrols.ftct.tagconfigmodifiers.HistoryProviderModifier;
import com.jlbcontrols.ftct.tagconfigmodifiers.OpcPathModifier;
import com.jlbcontrols.ftct.tagconfigmodifiers.OpcServerModifier;
import com.jlbcontrols.ftct.tagconfigmodifiers.TagGroupModifier;
import com.jlbcontrols.ftct.tagfilters.HistoryEnabledFilter;
import com.jlbcontrols.ftct.tagfilters.OpcFilter;
import com.jlbcontrols.ftct.tagfilters.OpcPathContainsFilter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class GatewayScriptModule extends AbstractScriptModule {
    private final Logger log;
    public GatewayScriptModule() {
        log = LogManager.getLogger(this.getClass());
    }

    @Override
    protected int updateOpcPathImpl(String parentPathString, String opcStartString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();
        try {
            TagPath parentPath = TagPathParser.parse(parentPathString);
            OpcFilter opcFilter = new OpcFilter();
            Map<String, TagPath> prefixFolderMap = TagMap.getPrefixFolderMap(parentPath);
            OpcPathModifier opcPathModifier = new OpcPathModifier(prefixFolderMap,opcStartString);
            FilteredUpdater filteredUpdater = new FilteredUpdater(parentPath,defaultProvider,opcFilter,opcPathModifier);
            return filteredUpdater.updateTagConfigs();
        } catch (Exception e){
            log.info("Error updating OPC paths.", e);
            return -1;
        }
    }

    @Override
    protected int updateOpcServerImpl(String parentPathString, String opcServerString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();
        try {
            TagPath parentPath = TagPathParser.parse(parentPathString);
            OpcFilter opcFilter = new OpcFilter();
            OpcServerModifier opcServerModifier = new OpcServerModifier(opcServerString);
            FilteredUpdater filteredUpdater = new FilteredUpdater(parentPath,defaultProvider,opcFilter,opcServerModifier);
            return filteredUpdater.updateTagConfigs();
        } catch (Exception e){
            log.info("Error updating OPC server.", e);
            return -1;
        }
    }

    @Override
    protected int updateHistoryProviderImpl(String parentPathString, String historyProviderString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();
        try {
            TagPath parentPath = TagPathParser.parse(parentPathString);
            HistoryEnabledFilter historyEnabledFilter = new HistoryEnabledFilter();
            HistoryProviderModifier historyProviderModifier = new HistoryProviderModifier(historyProviderString);
            FilteredUpdater filteredUpdater = new FilteredUpdater(parentPath,defaultProvider,historyEnabledFilter,historyProviderModifier);
            return filteredUpdater.updateTagConfigs();
        } catch (Exception e){
            log.info("Error updating History Provider.", e);
            return -1;
        }
    }

    @Override
    protected int updateTagGroupImpl(String parentPathString, String tagGroupString, String opcPathContainsString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();

        try {
            TagPath parentPath = TagPathParser.parse(parentPathString);
            OpcPathContainsFilter opcPathContainsFilter = new OpcPathContainsFilter(opcPathContainsString);
            TagGroupModifier tagGroupModifier = new TagGroupModifier(tagGroupString);
            FilteredUpdater filteredUpdater = new FilteredUpdater(parentPath,defaultProvider,opcPathContainsFilter,tagGroupModifier);
            return filteredUpdater.updateTagConfigs();
        } catch (Exception e){
            log.info("Error updating Tag Groups", e);
            return -1;
        }
    }

    @Override
    protected int folderizeImpl(String parentPathString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();
        TagPath parentPath = null;

        try {
            parentPath = TagPathParser.parse("[default]"+parentPathString);
        } catch (IOException e) {
            log.info("Error parsing parent tag path.", e);
        }

        TagRelocator tagRelocator = new TagRelocator(parentPath,defaultProvider);

        try {
            // Create new folders
            tagRelocator.addNewFolders();
        } catch (Exception e){
            log.info("Error creating new folders.", e);
            return -1;
        }

        try{
            // Get all atomic tags, and relocate them to the proper folder
            return tagRelocator.relocateTags();
        } catch (Exception e){
            log.info("Error moving tags to new folders.", e);
            return -1;
        }
    }

    @Override
    protected int removePrefixesImpl(String parentPathString) {
        TagProvider defaultProvider = GatewayHook.getDefaultProvider();

        try {
            TagPath parentPath = TagPathParser.parse("[default]"+parentPathString);

            TagNamePrefixRemover tagNamePrefixRemover = new TagNamePrefixRemover(parentPath,defaultProvider);

            // Create new folders
            return tagNamePrefixRemover.renameTags();

        } catch (Exception e){
            log.info("Error removing tag prefixes.", e);
            return -1;
        }
    }

}
