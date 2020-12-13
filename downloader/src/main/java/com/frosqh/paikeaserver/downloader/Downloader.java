package com.frosqh.paikeaserver.downloader;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.paikeaserver.database.PaikeaDataBase;
import com.frosqh.paikeaserver.settings.Settings;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.mpatric.mp3agic.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Downloader extends YoutubeDownloader {

    private final Map<String, Integer> percentageDownloads = new HashMap<>();
    private static Downloader instance;
    private static String response;

    class StreamGobbler extends Thread {
        InputStream is;
        String type;


        StreamGobbler (InputStream is, String type){
            this.is = is;
            this.type = type;
        }

        public void run(){
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + ">" + line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            p.waitFor();
            String current = System.getProperty("user.dir");
            String mp3Name = current+"/"+id+".mp3";
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

    public void downloadFromYoutubePlayListFirstStep(String id){
        try {
            Process p = Runtime.getRuntime().exec("youtube-dl -x --audio-format mp3 -o %(title)s.%(ext)s -i "+id);
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            int status = p.waitFor();
            if (status != 0)
                System.err.println("WHOOPS "+status);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
