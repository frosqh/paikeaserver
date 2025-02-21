package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Owner extends Model {

    public String name;

    public Owner(int id) {
        super(id);
    }

    public Owner(int id, String name) {
        super(id);
        this.name = name;
    }
}
