package com.frosqh.paikeaserver.database.board;

import com.frosqh.daolibrary.Model;

public class TagByGame extends Model {

    public int tag_id;
    public int game_id;

    public TagByGame(int id) {
        super(id);
    }

    public TagByGame(int id, int tag_id, int game_id){
        super(id);
        this.tag_id = tag_id;
        this.game_id = game_id;
    }

    public TagByGame(int id, Tag tag, int game_id){
        this(id, tag.getId(), game_id);
    }

    public TagByGame(int id, Tag tag, BoardGame game){
        this(id, tag.getId(), game.getId());
    }

    public TagByGame(int id, int tag_id, BoardGame game){
        this(id, tag_id, game.getId());
    }
}
