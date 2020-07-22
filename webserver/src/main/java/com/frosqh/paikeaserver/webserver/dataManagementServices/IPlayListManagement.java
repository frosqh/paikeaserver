package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.paikeaserver.database.PlayList;

import java.util.List;

public interface IPlayListManagement {

    List<PlayList> getAllPlayLists();
    PlayList addPlayList(String name, int creator_id);
    PlayList findOne(long id);
}
