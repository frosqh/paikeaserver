package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.paikeaserver.database.Song;

import java.util.List;

public interface ISongManagement {

    List<Song> getAllSongs();
    Song addSong(String title, String artist, String localurl, String weburl);
    Song findOne(long id);

}
