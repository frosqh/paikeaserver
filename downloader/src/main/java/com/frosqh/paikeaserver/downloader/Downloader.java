package com.frosqh.paikeaserver.downloader;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.paikeaserver.database.PaikeaDataBase;
import com.frosqh.paikeaserver.settings.Settings;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.mpatric.mp3agic.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Downloader extends YoutubeDownloader {

    private final Map<String, Integer> percentageDownloads = new HashMap<>();
    private static Downloader instance;
    private static String response;

    private Downloader(){
        super();
    }

    public static Downloader getInstance(){
        if (instance == null)
            instance = new Downloader();
        return instance;
    }

    public void downloadFromYoutube(String id, String title, String artist){
        System.out.println(id + "-" + title + "-" + artist);
        try {
            Process p = Runtime.getRuntime().exec("youtube-dl "+id+" -x --audio-format mp3 --id");
            p.waitFor();
            String mp3Name = id+".mp3";
            String current = System.getProperty("user.dir");
            System.out.println(current);
            Mp3File mp3File = new Mp3File(mp3Name);
            ID3v2 id3V2 = new ID3v24Tag();
            mp3File.setId3v2Tag(id3V2);
            id3V2.setTitle(title);
            id3V2.setArtist(artist);
            id3V2.setAudiofileUrl("https://youtube.com/watch?v="+id);
            mp3File.save(Settings.get("dirDL")+"/"+id3V2.getTitle()+".mp3");
            File file1 = new File(mp3Name);
            if (file1.exists())
                file1.delete();
            new PaikeaDataBase("../BotPaikea.db").refreshSongs();
        } catch (IOException | InvalidDataException | UnsupportedTagException | NotSupportedException | InterruptedException | ConnectionNotInitException e) {
            e.printStackTrace();
        }
    }

}
