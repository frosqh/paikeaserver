package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Location extends Model {

    public String name;

    public Location(int id) {
        super(id);
    }

    public Location(int id, String name){
        super(id);
        this.name = name;
    }
}
