package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class BoardGame extends Model {

    public String name;
    public int min_players;
    public int max_players;
    public int difficulty_id;

    public BoardGame(int id) {
        super(id);
    }

    public BoardGame(int id, String name, int min_players, int max_players, int difficulty_id) {
        super(id);
        this.name = name;
        this.min_players = min_players;
        this.max_players = max_players;
        this.difficulty_id = difficulty_id;
    }

    public BoardGame(int id, String name, int min_players, int max_players, Difficulty diff){
        this(id, name, min_players, max_players, diff.getId());
    }
}
