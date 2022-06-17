package com.frosqh.paikeaserver.webserver.dataManagementServices;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.board.BoardGame;

import java.util.List;

public class BoardManagement implements IBoardManagement{

    private final DAO<BoardGame> boardGameDAO = DAO.construct(BoardGame.class);

    @Override
    public List<BoardGame> getAllGames() {
        return boardGameDAO.getList();
    }

    @Override
    public BoardGame findOne(long id) {
        return boardGameDAO.find((int) id);
    }
}
