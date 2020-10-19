package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.paikeaserver.database.PlayList;
import com.frosqh.paikeaserver.database.Song;
import com.frosqh.paikeaserver.database.SongByPlayList;
import com.frosqh.paikeaserver.webserver.PaikeaApplication;
import com.frosqh.paikeaserver.webserver.dataManagementServices.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PlayListController {

    private final IPlayListManagement playListManager = new PlayListManagement();
    private final ISongByPlayListManagement songByPlayListManager = new SongByPlayListManagement();
    private final ISongManagement songManagement = new SongManagement();


    public void completeModel(Model model){
        List<PlayList> playLists = playListManager.getAllPlayLists();
        model.addAttribute("playlists",playLists);
        List<SongByPlayList> songByPlayLists = songByPlayListManager.getAllSongByPlayLists();
        List<Song> songs = songManagement.getAllSongs();
        Map<Integer, List<Song>> m = songs.stream().collect(Collectors.groupingBy(com.frosqh.daolibrary.Model::getId));
        Map<Integer, List<SongByPlayList>> l = new HashMap<>();
        if (songByPlayLists != null)
            l = songByPlayLists.stream().sorted(Comparator.comparing(a -> m.get(a.song_id).get(0).getTitle())).collect(Collectors.groupingBy(songByPlayList -> songByPlayList.playList_id));
        model.addAttribute("map",l);
        model.addAttribute("songs",songs);
        model.addAttribute("mapSong",m);
    }

    @RequestMapping("playlists")
    public String showAll(Model model){
        completeModel(model);
        return "playListView";
    }

    @RequestMapping("addplaylist")
    public String addPlayList(@RequestParam(value="name") String name,
                              Model model){
        playListManager.addPlayList(name,0 );
        completeModel(model);
        return "redirect:playlists";
    }

    @RequestMapping("addSong")
    public String addSong(@RequestParam MultiValueMap<String, String> params,
                          Model model
                          ){
        System.out.println(params);
        for (Song s : songManagement.getAllSongs()){
            if (params.containsKey("song"+s.getId())){
                songByPlayListManager.addSongToPlayList(s.getId(), Long.parseLong(params.get("playlist").get(0)));
            }
        }
        completeModel(model);

        return "redirect:playlists";
    }

    @RequestMapping("playplaylist")
    public String playPlayList(@RequestParam(value="id") long id,
                               Model model){
        PaikeaApplication.player.playPlaylist((int) id);
        completeModel(model);
        return "redirect:playlists";

    }
}
