package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Difficulty extends Model {

    public String label;
    public int value;

    public Difficulty(int id) {
        super(id);
    }

    public Difficulty(int id, String label, int value){
        super(id);
        this.label = label;
        this.value = value;
    }

}
