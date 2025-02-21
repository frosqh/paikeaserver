package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.DAO;
import com.frosqh.daolibrary.Model;

public class BlindTestGame extends Model {

    public int timecode;

    public int creator_id;

    public int playlist_id;

    public BlindTestGame(int id) {
        super(id);
    }

    public BlindTestGame(int id, int timecode, int creator_id, int playlist_id){
        super(id);
        this.timecode = timecode;
        this.creator_id = creator_id;
        this.playlist_id = playlist_id;
    }

    public User getCreator(){
        DAO<User> userDAO = DAO.construct(User.class);
        return userDAO.find(creator_id);
    }

    public PlayList getPlayList(){
        DAO<PlayList> playListDAO = DAO.construct(PlayList.class);
        return playListDAO.find(playlist_id);
    }
}
