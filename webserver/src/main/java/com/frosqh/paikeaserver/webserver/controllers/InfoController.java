package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.player.exceptions.EmptyHistoryException;
import com.frosqh.paikeaserver.player.exceptions.PauseException;
import com.frosqh.paikeaserver.player.exceptions.PlayException;
import com.frosqh.paikeaserver.webserver.PaikeaApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class InfoController {

    @GetMapping("/infos")
    public Map<String,String> getInfos(){
    /*public Song getInfos(){*/
        Map<String,String> model = new HashMap<>();
        String inf = PaikeaApplication.player.getInfosSendable();
        String[] infos = inf.split("â–¬");
        String[] prevNext = PaikeaApplication.player.getPrevNext().split("â–¬");
        int timeCode = Float.valueOf(infos[3]).intValue() / 1000;
        String current = "";
        int duration = Float.valueOf(infos[5]).intValue()/1000;
        int mC = timeCode/60;
        int sC = timeCode%60;
        if (mC > 0){
            current = mC +"m";
        }
        current += sC + "s";
        String total = "";
        int mD = duration/60;
        int sD = duration%60;
        if (mD > 0){
            total = mD + "m";
        }
        total += sD + "s";
        if (infos[2].split(" - ").length < 2){
            model.put("title","Never Gonna Give You Up");
            model.put("artist", "Rick Astley");
        }
        else{
            model.put("title",infos[2].split(" - ")[1]);
            model.put("artist",infos[2].split(" - ")[0]);
        }
        model.put("current",current);
        model.put("total",total);
        model.put("progress",String.valueOf(timeCode/(1.*duration)*100));
        model.put("playing", infos[1].equals("true") ?"1":"0");
        model.put("prev",prevNext[0]);
        model.put("next",prevNext[1]);
        /**/
        Song song = new Song(0,model.get("title"),model.get("artist"),"44","12");
        //return song;
        return model;
    }

    @RequestMapping("/prev")
    public void prev() {
        try {
            PaikeaApplication.player.prev();
        } catch (EmptyHistoryException ignored) {
        }
    }

    @RequestMapping("/next")
    public void next(){
        PaikeaApplication.player.next();
    }

    @RequestMapping("/play")
    public void play(){
        try {
            PaikeaApplication.player.play();
        } catch (PlayException ignored) {
        }
    }

    @RequestMapping("/pause")
    public void pause(){
        try {
            PaikeaApplication.player.pause();
        } catch (PauseException ignored) {
        }
    }

    @RequestMapping("/seekback")
    public void seekback(){
        try {
            PaikeaApplication.player.seekback();
        } catch (PlayException ignored) {
        }
    }

    @RequestMapping("/seekfor")
    public void seekfor(){
        try {
            PaikeaApplication.player.seekfor();
        } catch (PlayException ignored) {
        }
    }

    @RequestMapping("seekto")
    public void seekto(@RequestParam(value="seekValue",required=true) String seekTime,
                         Model model){
        System.out.println("CAROTE");
        System.out.println(seekTime);
        try {
            PaikeaApplication.player.seekto(Float.parseFloat(seekTime));
        } catch (PlayException ignored) {
        }
    }
}
