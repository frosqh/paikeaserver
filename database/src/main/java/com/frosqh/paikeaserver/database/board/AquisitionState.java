package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class AquisitionState extends Model {

    public String label;
    public int value;


    public AquisitionState(int id) {
        super(id);
    }

    public AquisitionState(int id, String label, int value) {
        super(id);
        this.label = label;
        this.value = value;
    }
}
