package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class FacebookAccount extends Model {

    int userID;

    public FacebookAccount(int id) {
        super(id);
    }

    public FacebookAccount(int id, int userID){
        super(id);
        this.userID = userID;
    }

}
