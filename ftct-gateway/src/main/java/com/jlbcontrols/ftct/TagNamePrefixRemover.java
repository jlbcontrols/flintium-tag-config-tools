package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.tags.config.CollisionPolicy;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.inductiveautomation.ignition.gateway.tags.model.GatewayTagManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TagNamePrefixRemover {

    TagPath parentPath;
    TagProvider provider;
    Map<String,TagPath> prefixFolderMap;

    public TagNamePrefixRemover(TagPath parentPath, TagProvider provider) {
        this.parentPath = parentPath;
        this.provider = provider;
        this.prefixFolderMap = TagMap.getPrefixFolderMap(parentPath);
    }


    public int renameTags() throws Exception {
        GatewayTagManager tagManager = GatewayHook.getContext().getTagManager();
        List<TagPath> originalTagPaths = TagMap.getPathsInFolders(parentPath,provider);
        int tagCount = 0;
        for(TagPath otPath : originalTagPaths){
            for (Map.Entry<String, TagPath> entry : prefixFolderMap.entrySet()){
                if (otPath.getItemName().startsWith(entry.getKey())){
                    String newName = otPath.getItemName().replace(entry.getKey(),"");
                    List<QualityCode>results = tagManager.renameTag(otPath, newName, CollisionPolicy.Overwrite).get(30, TimeUnit.SECONDS);
                    QualityCode qc = results.get(0);
                    if (qc.isNotGood()) {
                        throw new Exception(String.format("Rename operation returned bad result '%s'", qc.toString()));
                    }else{
                        tagCount++;
                    }
                }
            }
        }
        return tagCount;
    }

}
