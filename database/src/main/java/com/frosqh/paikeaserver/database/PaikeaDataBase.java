package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.daolibrary.DAO;
import com.frosqh.daolibrary.DataBase;
import com.frosqh.paikeaserver.file_explorer.DiskFileExplorer;
import com.frosqh.paikeaserver.settings.Settings;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class PaikeaDataBase extends DataBase {

    public PaikeaDataBase(String name) throws ConnectionNotInitException {
        super(name);
    }

    public void refreshSongs() throws InvalidDataException, IOException, UnsupportedTagException {
        String[] files = Settings.get("dirs").split(";");
        DAO<Song> songDAO = DAO.construct(Song.class);
        List<Song> songs = songDAO.getList();
        for (Song song : songs){
            String preURL = song.getLocalurl();
            if (Arrays.stream(files).noneMatch(preURL::startsWith)){
                songDAO.delete(song);
            }
        }
        for (String file : files){
            LocalDateTime now = LocalDateTime.now();
            DiskFileExplorer dfe = new DiskFileExplorer(file, true);
            songs = songDAO.getList();
            List<String> paths = dfe.list();

            for (Song song : songs){
                String preURL = song.getLocalurl();
                String URL = preURL.replace(file, "").substring(1);
                if (!preURL.contains(URL) && !paths.contains(URL)) songDAO.delete(song); //If song on database but not on disk
                else paths.remove(URL);
            }
            for (String path : paths){
                Song temp;
                Mp3File mp3File = new Mp3File(file+"\\"+path);
                temp = new Song(-1, getTitle(mp3File), getArtist(mp3File), file+"\\"+path, "" );
                songDAO.create(temp);

            }
            LocalDateTime then = LocalDateTime.now();
            System.out.println("Loading songs : "+ ChronoUnit.SECONDS.between(now, then)+"s");
            System.out.println(songDAO.getList().size());
        }
    }

    public String getTitle(Mp3File mp3File){
        if (mp3File.hasId3v1Tag() && mp3File.getId3v2Tag().getTitle()!=null)
            return mp3File.getId3v1Tag().getTitle();
        if (mp3File.hasId3v2Tag() && mp3File.getId3v2Tag().getTitle()!=null)
            return mp3File.getId3v2Tag().getTitle();
        return "Unknown";
    }

    public String getArtist(Mp3File mp3File){
        if (mp3File.hasId3v1Tag() && mp3File.getId3v1Tag().getArtist()!=null)
            return mp3File.getId3v1Tag().getArtist();
        if (mp3File.hasId3v2Tag() && mp3File.getId3v2Tag().getArtist()!=null)
            return mp3File.getId3v2Tag().getArtist();
        return "Unknown";
    }
}
