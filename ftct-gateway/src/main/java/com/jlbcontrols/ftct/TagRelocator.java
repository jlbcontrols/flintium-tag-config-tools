package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.tags.config.BasicTagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.CollisionPolicy;
import com.inductiveautomation.ignition.common.tags.config.TagConfiguration;
import com.inductiveautomation.ignition.common.tags.config.types.TagObjectType;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.inductiveautomation.ignition.gateway.tags.model.GatewayTagManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TagRelocator {

    TagPath parentPath;
    TagProvider provider;
    Map<String,TagPath> prefixFolderMap;

    public TagRelocator(TagPath parentPath, TagProvider provider) {
        this.parentPath = parentPath;
        this.provider = provider;
        this.prefixFolderMap = TagMap.getPrefixFolderMap(parentPath);
    }

    public void addNewFolders() throws ExecutionException, InterruptedException {
        List<TagConfiguration> newFolderConfigs = new ArrayList<>();

        for (TagPath folderPath : prefixFolderMap.values()){
            TagConfiguration folderConfig = BasicTagConfiguration.createNew(folderPath);
            folderConfig.setType(TagObjectType.Folder);
            newFolderConfigs.add(folderConfig);
        }
        provider.saveTagConfigsAsync(newFolderConfigs, CollisionPolicy.Abort).get();
    }

    /**
    * Sort into map of <destination folder path, List<originalTagPath>
     */
    public Map<TagPath,List<TagPath>> getRelocationMap(List<TagPath> originalTagPaths){
        Map<TagPath,List<TagPath>> relocationMap = getEmptyRelocationMap();

        for(TagPath otPath : originalTagPaths){
            for (Map.Entry<String, TagPath> entry : prefixFolderMap.entrySet()){
                if (otPath.getItemName().startsWith(entry.getKey())){
                    relocationMap.get(entry.getValue()).add(otPath);
                }
            }
        }
        return relocationMap;
    }

    public Map<TagPath,List<TagPath>> getEmptyRelocationMap(){
        HashMap<TagPath,List<TagPath>> emptyRelocationMap = new HashMap<>();
        for (Map.Entry<String, TagPath> entry : prefixFolderMap.entrySet()){
            emptyRelocationMap.put(entry.getValue(),new ArrayList<>());
        }
        return emptyRelocationMap;
    }

    public int relocateTags() throws Exception {
        ArrayList<TagPath> originalTagPaths = TagUtils.findPaths(provider,parentPath,false,false);
        int tagCount = 0;
        Map<TagPath,List<TagPath>> relocationMap = getRelocationMap(originalTagPaths);
        for (Map.Entry<TagPath,List<TagPath>> entry : relocationMap.entrySet()){
            relocateTagsToFolder(entry.getKey(),entry.getValue());
            tagCount+=entry.getValue().size();
        }
        return tagCount;
    }

    public void relocateTagsToFolder(TagPath destinationFolderPath, List<TagPath> originalTagPaths) throws Exception {
        if(originalTagPaths.size()!=0){
            GatewayTagManager tagManager = GatewayHook.getContext().getTagManager();
            List<QualityCode>results = tagManager.moveTagsAsync(originalTagPaths, destinationFolderPath, false, CollisionPolicy.Overwrite).get(30, TimeUnit.SECONDS);
            QualityCode qc = results.get(0);
            if (qc.isNotGood()) {
                throw new Exception(String.format("Move operation returned bad result '%s'", qc.toString()));
            }
        }
    }

}
