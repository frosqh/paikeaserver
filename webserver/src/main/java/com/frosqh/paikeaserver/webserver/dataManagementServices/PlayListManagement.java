package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.PlayList;

import java.util.List;

public class PlayListManagement implements IPlayListManagement {

    private final DAO<PlayList> playListDAO = DAO.construct(PlayList.class);

    @Override
    public List<PlayList> getAllPlayLists() {
        return playListDAO.getList();
    }

    @Override
    public PlayList addPlayList(String name, int creator_id) {
        return playListDAO.create(new PlayList(0,name, creator_id));
    }

    @Override
    public PlayList findOne(long id) {
        return playListDAO.find((int) id);
    }
}
