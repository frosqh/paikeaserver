package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class Component extends Model {

    public int game_id;
    public String name;
    public int owner_id;
    public int locale_id;
    public int storing_place_id;
    public int movable_id;
    public int state_id;

    public Component(int id) {
        super(id);
    }

    public Component(int id, int game_id, String name, int owner_id, int locale_id, int storing_place_id, int movable_id, int state_id) {
        super(id);
        this.game_id = game_id;
        this.name = name;
        this.owner_id = owner_id;
        this.locale_id = locale_id;
        this.storing_place_id = storing_place_id;
        this.movable_id = movable_id;
        this.state_id = state_id;
    }
}
