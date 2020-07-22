package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class SongByPlayList extends Model {
    public int song_id;
    public int playList_id;

    public SongByPlayList(int id) {
        super(id);
    }

    public SongByPlayList(int id, int song_id, int playList_id){
        super(id);
        this.song_id = song_id;
        this.playList_id = playList_id;
    }

    public SongByPlayList(int id, Song song, int playList_id){
        this(id, song.getId(), playList_id);
    }

    public SongByPlayList(int id, int song_id, PlayList playList){
        this(id, song_id, playList.getId());
    }

    public SongByPlayList(int id, Song song, PlayList playList){
        this(id, song.getId(), playList.getId());
    }

    @Override
    public String toString() {
        return "{Id: "+getId()+"; song_id: "+song_id+"; playList_id: "+playList_id+"}";
    }
}
