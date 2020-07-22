package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.PlayList;
import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.database.SongByPlayList;

import java.util.List;

public class SongByPlayListManagement implements ISongByPlayListManagement{

    private DAO<SongByPlayList> songByPlayListDAO = DAO.construct(SongByPlayList.class);

    @Override
    public List<SongByPlayList> getAllSongByPlayLists() {
        return songByPlayListDAO.getList();
    }

    @Override
    public SongByPlayList addSongToPlayList(Song s, PlayList p) {
        return songByPlayListDAO.create(new SongByPlayList(0,s.getId(),p.getId()));
    }

    @Override
    public SongByPlayList addSongToPlayList(long song_id, long playlist_id) {
        return songByPlayListDAO.create(new SongByPlayList(0,(int) song_id,(int) playlist_id));
    }

    @Override
    public SongByPlayList findOne(long id) {
        return songByPlayListDAO.find((int) id);
    }
}
