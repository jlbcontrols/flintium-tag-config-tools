package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.browsing.BrowseFilter;
import com.inductiveautomation.ignition.common.browsing.Results;
import com.inductiveautomation.ignition.common.config.BoundValue;
import com.inductiveautomation.ignition.common.tags.browsing.NodeDescription;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.TagConfigurationModel;
import com.inductiveautomation.ignition.common.tags.config.types.OpcTagTypeProperties;
import com.inductiveautomation.ignition.common.tags.config.types.TagObjectType;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TagUtils {

    public static ArrayList<TagPath> findAtomicPaths(TagProvider provider, TagPath parentPath, ArrayList<TagPath> paths, boolean recursive) throws Exception {
        Collection<NodeDescription> nodes = getChildNodeDescriptions(provider, parentPath);
        for(NodeDescription node: nodes) {
            if(node.getObjectType() == TagObjectType.AtomicTag){
                TagPath path = parentPath.getChildPath(node.getName());
                paths.add(path);
            }
            if(recursive && (node.getObjectType() == TagObjectType.Folder || node.getObjectType() == TagObjectType.UdtType)){
                TagPath newPath = parentPath.getChildPath(node.getName());
                findAtomicPaths(provider, newPath, paths, true);
            }
        }
        return paths;
    }

    public static Collection<NodeDescription> getChildNodeDescriptions(TagProvider provider, TagPath parentPath) throws Exception {
        Results<NodeDescription> results = provider.browseAsync(parentPath, BrowseFilter.NONE).get(30, TimeUnit.SECONDS);
        if(results.getResultQuality().isNotGood()) {
            throw new Exception("Bad quality results: "+ results.getResultQuality().toString());
        }
        Collection<NodeDescription> nodes = results.getResults();
        return nodes;
    }

    public static ArrayList<TagPath> findPaths(TagProvider provider, TagPath parentPath, boolean recursiveFolder, boolean recursiveUdt) throws Exception {
        return findPaths(provider, parentPath, new ArrayList<>(), recursiveFolder, recursiveUdt);
    }

    public static ArrayList<TagPath> findPaths(TagProvider provider, TagPath parentPath, ArrayList<TagPath> paths, boolean recursiveFolder, boolean recursiveUdt) throws Exception {
        Collection<NodeDescription> nodes = getChildNodeDescriptions(provider, parentPath);
        for(NodeDescription node: nodes) {
            TagPath path = parentPath.getChildPath(node.getName());
            paths.add(path);
            if((recursiveFolder && node.getObjectType() == TagObjectType.Folder) || (recursiveUdt && node.getObjectType() == TagObjectType.UdtType)){
                TagPath newPath = parentPath.getChildPath(node.getName());
                findAtomicPaths(provider, newPath, paths, true);
            }
        }
        return paths;
    }

    public static List<TagConfiguration> modelsToConfigs(List<TagConfigurationModel> tagConfigModels){
        List<TagConfiguration> tagConfigs = new ArrayList<>();
        for (TagConfigurationModel tagConfigModel : tagConfigModels){
            tagConfigs.add(tagConfigModel);
        }
        return tagConfigs;
    }

    public static List<TagConfiguration> getTagConfigs(TagProvider provider, List<TagPath> tagPaths) throws Exception {
        List<TagConfigurationModel> tagConfigModels = provider.getTagConfigsAsync(tagPaths, false, true).get(30, TimeUnit.SECONDS);
        return modelsToConfigs(tagConfigModels);
    }

    public static String getOpcPathString(TagConfiguration tagConfig){
        String opcPathString = tagConfig.get(OpcTagTypeProperties.OPCItemPath);
        if(opcPathString==null){
            BoundValue oldOpcPathBound = tagConfig.getBoundValue(OpcTagTypeProperties.OPCItemPath);
            opcPathString = oldOpcPathBound.getBinding();
        }
        return opcPathString;
    }

}
