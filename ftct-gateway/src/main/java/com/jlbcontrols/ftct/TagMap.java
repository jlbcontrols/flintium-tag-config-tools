package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagMap {

    public static Map<String,TagPath> getPrefixFolderMap(TagPath parentPath){

        HashMap<String,TagPath> map = new HashMap<>();
        map.put("Val_", parentPath.getChildPath("Val"));
        map.put("Cfg_", parentPath.getChildPath("Config"));
        map.put("Inp_", parentPath.getChildPath("Input"));
        map.put("Out_", parentPath.getChildPath("Output"));
        map.put("Nrdy_", parentPath.getChildPath("Nrdy"));
        map.put("Err_", parentPath.getChildPath("Error"));
        map.put("Sts_", parentPath.getChildPath("Status"));

        TagPath cmdSubFolder = parentPath.getChildPath("Cmd");
        map.put("OCmd_", cmdSubFolder.getChildPath("O"));
        map.put("MCmd_", cmdSubFolder.getChildPath("M"));
        map.put("PCmd_", cmdSubFolder.getChildPath("P"));
        map.put("XCmd_", cmdSubFolder.getChildPath("X"));

        TagPath readySubFolder = parentPath.getChildPath("Ready");
        map.put("ORdy_", readySubFolder.getChildPath("O"));
        map.put("MRdy_", readySubFolder.getChildPath("M"));

        TagPath setSubFolder = parentPath.getChildPath("Set");
        map.put("OSet_", setSubFolder.getChildPath("O"));
//        map.put("MSet_", setSubFolder.getChildPath("M"));
        map.put("PSet_", setSubFolder.getChildPath("P"));
        map.put("XSet_", setSubFolder.getChildPath("X"));

        return map;
    }

    public static List<TagPath> getPathsInFolders(TagPath parentPath, TagProvider provider) throws Exception {
        List<TagPath> tagPaths = new ArrayList<>();
        Map<String,TagPath> prefixFolderMap = getPrefixFolderMap(parentPath);
        for(TagPath folderPath : prefixFolderMap.values()){
            tagPaths.addAll(TagUtils.findPaths(provider,folderPath,true,false));
        }
        return tagPaths;
    }
}
