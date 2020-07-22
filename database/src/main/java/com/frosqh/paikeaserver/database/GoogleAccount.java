package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class GoogleAccount extends Model {

    private String name;

    protected GoogleAccount(int id) {
        super(id);
    }

    public GoogleAccount(int id, String name){
        super(id);
        this.name = name;
    }
}
