package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Locale extends Model {

    public String name;
    public String code;

    public Locale(int id) {
        super(id);
    }

    public Locale(int id, String name, String code){
        super(id);
        this.name = name;
        this.code = code;
    }
}
