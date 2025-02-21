package com.frosqh.paikeaserver.webserver.controllers.board;

import com.frosqh.paikeaserver.webserver.dataManagementServices.BoardManagement;
import com.frosqh.paikeaserver.webserver.dataManagementServices.IBoardManagement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

    private final IBoardManagement boardManager = new BoardManagement();

    @RequestMapping("boardgames")
    public String showAll(Model model){
        model.addAttribute("games", boardManager.getAllGames());
        System.out.print(model.asMap().values());
        return "board/boardGame";
    }
}
