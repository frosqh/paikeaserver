package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.paikeaserver.database.PlayList;
import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.database.SongByPlayList;

import java.util.List;

public interface ISongByPlayListManagement {

    List<SongByPlayList> getAllSongByPlayLists();
    SongByPlayList addSongToPlayList(Song s, PlayList p);
    SongByPlayList addSongToPlayList(long song_id, long playlist_id);
    SongByPlayList findOne(long id);
}
