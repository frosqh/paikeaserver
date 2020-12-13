package com.frosqh.paikeaserver.webserver.controllers;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.paikeaserver.database.PaikeaDataBase;
import com.frosqh.paikeaserver.downloader.Downloader;
import com.frosqh.paikeaserver.file_explorer.DiskFileExplorer;
import com.frosqh.paikeaserver.settings.Settings;
import com.mpatric.mp3agic.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class DownloadController {

    private enum Service {
        YOUTUBE("youtube") ,
        SPOTIFY("spotify");
        private String name;
        private Service(String name) {
            this.name = name;
        }
    }

    private enum SongType {
        SONG ("song"),
        PLAYLIST ("playlist");
        private String name;
        private SongType(String name) {
            this.name = name;
        }
    }

    @RequestMapping("download")
    public String showPage(Model model){

        return "download";
    }

    @RequestMapping("reqdownload")
    public String download(HttpServletRequest request,
                           @RequestParam(value="id") String id,
                           @RequestParam(value="title") String title,
                           @RequestParam(value="artist") String artist,
                           @RequestParam(value="service") String service,
                           @RequestParam(value="type") String type,
                           Model model
                            ){
        switch (service) {
            case "youtube" -> {
                switch (type) {
                    case "song" -> Downloader.getInstance().downloadFromYoutube(id, title, artist);
                    case "playlist" -> {
                        Downloader.getInstance().downloadFromYoutubePlayListFirstStep(id);
                        List<String> mp3files = new DiskFileExplorer(System.getProperty("user.dir"), false).list();
                        model.addAttribute("songs", mp3files);
                        return "cdownload";
                    }
                }
            }
            case "spotify" -> throw new NotImplementedException("SPOTIFY");
        }

        return "redirect:download";
    }


    @RequestMapping("/cdownload")
    public String completeDownload(HttpServletRequest request,
                                   @RequestParam(value = "title[]") List<String> titres,
                                   @RequestParam(value = "artist[]") List<String> artists,
                                   Model model) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException, ConnectionNotInitException {
        List<String> mp3files = new DiskFileExplorer(System.getProperty("user.dir"), false).list();
        int n = titres.size();
        for (int i=0; i<n; i++){
            String mp3Name = mp3files.get(i);
            Mp3File mp3File = new Mp3File(mp3Name);
            ID3v2 id3V2 = new ID3v24Tag();
            mp3File.setId3v2Tag(id3V2);
            id3V2.setTitle(titres.get(i));
            id3V2.setArtist(artists.get(i));
            mp3File.save(Settings.get("dirDL")+"/"+id3V2.getTitle()+".mp3");
            File file1 = new File(mp3Name);
            new PaikeaDataBase("../BotPaikea.db").refreshSongs();
            if (file1.exists())
                file1.delete();
        }
        return "redirect:download";
    }
}
