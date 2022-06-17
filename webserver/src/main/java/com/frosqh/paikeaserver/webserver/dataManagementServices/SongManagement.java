package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.Song;

import java.util.List;

public class SongManagement implements ISongManagement {
    private final DAO<Song> songDAO = DAO.construct(Song.class);

    @Override
    public List<Song> getAllSongs() {
        DAO<Song> myDAO = DAO.construct(Song.class);
        return myDAO.getList();
    }

    @Override
    public Song addSong(String title, String artist, String localurl, String weburl) {
        return songDAO.create(new Song(0,title,artist,localurl,weburl));
    }

    @Override
    public Song findOne(long id) {
        return songDAO.find((int) id);
    }
}
