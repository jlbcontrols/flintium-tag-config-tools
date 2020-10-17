package com.jlbcontrols.ftct;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptArg;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;

public abstract class AbstractScriptModule implements TagEditorBlackBox {

    static {
        BundleUtil.get().addBundle(
            AbstractScriptModule.class.getSimpleName(),
            AbstractScriptModule.class.getClassLoader(),
            AbstractScriptModule.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int updateOpcPath(
        @ScriptArg("parentPath") String parentPath,
        @ScriptArg("opcStart") String opcStart) {

        return updateOpcPathImpl(parentPath, opcStart);
    }
    protected abstract int updateOpcPathImpl(String parentPath, String opcStart);


    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int updateOpcServer(
            @ScriptArg("parentPath") String parentPath,
            @ScriptArg("opcStart") String opcServer) {  //TODO: Change this to "opcServer"

        return updateOpcServerImpl(parentPath, opcServer);
    }
    protected abstract int updateOpcServerImpl(String parentPath, String opcServer);


    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int updateHistoryProvider(
            @ScriptArg("parentPath") String parentPath,
            @ScriptArg("historyProvider") String historyProvider) {

        return updateHistoryProviderImpl(parentPath, historyProvider);
    }
    protected abstract int updateHistoryProviderImpl(String parentPath, String historyProvider);

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int updateTagGroup(
        @ScriptArg("parentPath") String parentPath,
        @ScriptArg("tagGroup") String tagGroup,
        @ScriptArg("opcPathContains") String opcPathContains) {

        return updateTagGroupImpl(parentPath, tagGroup, opcPathContains);
    }
    protected abstract int updateTagGroupImpl(String parentPath, String tagGroup, String opcPathContains);


    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int folderize(
        @ScriptArg("parentPath") String parentPath) {

        return folderizeImpl(parentPath);
    }
    protected abstract int folderizeImpl(String parentPath);


    @Override
    @ScriptFunction(docBundlePrefix = "AbstractScriptModule")
    public int removePrefixes(
        @ScriptArg("parentPath") String parentPath) {

        return removePrefixesImpl(parentPath);
    }
    protected abstract int removePrefixesImpl(String parentPath);
}
