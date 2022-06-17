package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Movability extends Model {

    public String label;
    public int value;

    public Movability(int id) {
        super(id);
    }

    public Movability(int id, String label, int value){
        super(id);
        this.label = label;
        this.value = value;
    }
}
