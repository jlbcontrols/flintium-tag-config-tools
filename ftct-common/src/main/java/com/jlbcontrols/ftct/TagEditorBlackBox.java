package com.jlbcontrols.ftct;

public interface TagEditorBlackBox {

    int updateOpcPath(String parentPath, String opcStart);

    int updateOpcServer(String parentPath, String opcServer);

    int updateHistoryProvider(String parentPath, String historyProvider);

    int updateTagGroup(String parentPath, String tagGroup, String opcPathContains);

    int folderize(String parentPath);

    int removePrefixes(String parentPath);
}
