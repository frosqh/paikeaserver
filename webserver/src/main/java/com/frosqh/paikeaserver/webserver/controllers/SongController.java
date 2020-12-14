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

    private void completeModel(Model model, boolean musicAdded, String added){
        List<Song> songs = songManager.getAllSongs();
        model.addAttribute("hasBeenAdded", musicAdded);
        model.addAttribute("added", added);
        model.addAttribute("songs",songs);
    }

    @RequestMapping("songs")
    public String showAll(Model model){
        completeModel(model, false, null);
        return "songView";
    }

    @RequestMapping("playsong")
    public String playSong(@RequestParam(value="id") long id,
            Model model){
        Song song = songManager.findOne(id);
        PaikeaApplication.player.add(song);
        completeModel(model, true, song.getTitle());
        return "songView";
    }

    //TODO -> Regarder s'i ne serait pas en fait putain de possible d'afficher la queue, ce qui permettrait possiblement une lecture plus facile de la fonctionnalité d'ajout ?

}
