package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.webserver.PaikeaApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    String message;
    Long prevNano;

    @RequestMapping("/apiinfos")
    public Map<String, List<Object>> getApiInfos() {
        Map<String,List<Object>> model2 = new HashMap<>();
        Map<String,String> model = new HashMap<>();
        String inf = PaikeaApplication.player.getInfosSendable();
        Map<String, String> frame1 = new HashMap<>();
        Map<String, Object> frame2 = new HashMap<>();
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
        frame1.put("text", model.get("title"));
        frame1.put("icon", "a43323");


        Map<String, String> goalData = new HashMap<>();
        goalData.put("start", "0");
        goalData.put("current", String.valueOf(timeCode));
        goalData.put("end", String.valueOf(duration));
        goalData.put("unit", "s");

        frame2.put("goalData", goalData);
        frame2.put("icon", "a43323");


        Map<String, String> frame3 = new HashMap<>();
        frame3.put("text", message);
        frame3.put("icon", "a43323");
        frame3.put("index", "0");

        model2.put("frames", new ArrayList<>());
        //model2.get("frames").add(frame1);
        //model2.get("frames").add(frame2);
        model2.get("frames").add(frame3);




        return model2;
    }

    @RequestMapping("/changemessage")
    public String getApiInfos(@RequestParam(value="message") String message,
                                                 Model model) {
        this.message = message;
        return "redirect:playlist";
    }

    @RequestMapping("/wait")
    public String waitForDoublePress() {
        Long currentNano = System.nanoTime();
        new InfoController().pauseplay();
        if (prevNano != null && currentNano - prevNano <= 1e9){
            new InfoController().next();
        }
        prevNano = currentNano;
        return "redirect:playlist";
    }
}
