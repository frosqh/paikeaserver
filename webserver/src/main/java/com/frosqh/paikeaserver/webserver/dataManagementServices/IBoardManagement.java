package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.paikeaserver.database.board.BoardGame;

import java.util.List;

public interface IBoardManagement {

    List<BoardGame> getAllGames();
    BoardGame findOne(long id);

}
