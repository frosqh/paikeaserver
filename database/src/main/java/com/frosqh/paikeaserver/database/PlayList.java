package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class PlayList extends Model {

    public String name;
    public int creator_id;

    public PlayList(int id) {
        super(id);
    }

    public PlayList(int id, String name, int creator_id){
        super(id);
        this.name = name;
        this.creator_id = creator_id;
    }

    public PlayList(int id, String name, User creator){
        this(id, name, creator.getId());
    }
}
