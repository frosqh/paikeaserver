package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.webserver.PaikeaApplication;
import com.frosqh.paikeaserver.webserver.dataManagementServices.ISongManagement;
import com.frosqh.paikeaserver.webserver.dataManagementServices.SongManagement;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SongController {

    private final ISongManagement songManager = new SongManagement();

    private void completeModel(Model model){
        List<Song> songs = songManager.getAllSongs();
        model.addAttribute("songs",songs);
    }

    @RequestMapping("songs")
    public String showAll(Model model){
        completeModel(model);
        return "songView";
    }

    @RequestMapping("playsong")
    public String playSong(@RequestParam(value="id") long id,
            Model model){
        PaikeaApplication.player.add(songManager.findOne(id));
        completeModel(model);
        return "redirect:songs";
    }


}
