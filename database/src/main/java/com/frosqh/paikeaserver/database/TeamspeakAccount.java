package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class TeamspeakAccount extends Model {
    int userID;

    public TeamspeakAccount(int id) {
        super(id);
    }

    public TeamspeakAccount(int id, int userID){
        super(id);
        this.userID = userID;
    }
}
