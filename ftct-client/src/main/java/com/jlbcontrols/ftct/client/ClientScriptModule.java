package com.jlbcontrols.ftct.client;

import com.jlbcontrols.ftct.AbstractScriptModule;
import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import com.jlbcontrols.ftct.TagEditorBlackBox;

public class ClientScriptModule extends AbstractScriptModule {

    private final TagEditorBlackBox rpc;

    public ClientScriptModule() {
        rpc = ModuleRPCFactory.create(
            "com.jlbcontrols.ftct",
                TagEditorBlackBox.class
        );
    }

    @Override
    protected int updateOpcPathImpl(String parentPath, String opcItemPath) {
        return rpc.updateOpcPath(parentPath, opcItemPath);
    }

    @Override
    protected int updateOpcServerImpl(String parentPath, String opcServer) {
        return rpc.updateOpcServer(parentPath, opcServer);
    }

    @Override
    protected int updateHistoryProviderImpl(String parentPath, String historyProvider) {
        return rpc.updateHistoryProvider(parentPath, historyProvider);
    }

    @Override
    protected int updateTagGroupImpl(String parentPath, String tagGroup, String opcPathContains) {
        return rpc.updateTagGroup(parentPath, tagGroup, opcPathContains);
    }

    @Override
    protected int folderizeImpl(String parentPath) {
        return rpc.folderize(parentPath);
    }

    @Override
    protected int removePrefixesImpl(String parentPath) {
        return rpc.removePrefixes(parentPath);
    }
}
