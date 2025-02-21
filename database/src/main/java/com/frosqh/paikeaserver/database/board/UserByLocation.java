package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class UserByLocation extends Model {

    public int owner_id;
    public int location_id;

    public UserByLocation(int id) {
        super(id);
    }

    public UserByLocation(int id, int owner_id, int location_id){
        super(id);
        this.owner_id = owner_id;
        this.location_id = location_id;
    }
}
