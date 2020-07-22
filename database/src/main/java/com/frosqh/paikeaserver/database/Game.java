package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class Game extends Model {

    public String name;

    public Game(int id){
        super(id);
    }

    public Game(int id, String name){
        super(id);
        this.name = name;
    }
}
