package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Tag extends Model {

    public String label;

    public Tag(int id) {
        super(id);
    }

    public Tag(int id, String label){
        super(id);
        this.label = label;
    }
}
