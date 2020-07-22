package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class MailAccount extends Model {

    int userID;

    public MailAccount(int id) {
        super(id);
    }

    public MailAccount(int id, int userID){
        super(id);
        this.userID = userID;
    }
}
