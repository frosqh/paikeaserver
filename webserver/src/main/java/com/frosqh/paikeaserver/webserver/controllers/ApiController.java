package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.webserver.PaikeaApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @RequestMapping("/apiinfos")
    public Map<String, List<Map<String,String>>> getApiInfos() {
        Map<String,List<Map<String, String>>> model2 = new HashMap<>();
        Map<String,String> model = new HashMap<>();
        String inf = PaikeaApplication.player.getInfosSendable();
        Map<String, String> frame = new HashMap<>();
        String[] infos = inf.split("▬");
        String[] prevNext = PaikeaApplication.player.getPrevNext().split("▬");
        int timeCode = Float.valueOf(infos[3]).intValue() / 1000;
        String volume = infos[4];
        String current = "";
        int duration = Float.valueOf(infos[5]).intValue()/1000;
        // TODO : Handle code duplication
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
        frame.put("text", model.get("title"));
        frame.put("icon", "a43323");



        return model2;
    }
}
