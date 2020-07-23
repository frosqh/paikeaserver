package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.ConnectionNotInitException;
import com.frosqh.daolibrary.DAO;
import com.frosqh.daolibrary.DataBase;
import com.frosqh.paikeaserver.file_explorer.DiskFileExplorer;
import com.frosqh.paikeaserver.settings.Settings;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PaikeaDataBase extends DataBase {

    private final String sep = "_-_";

    public PaikeaDataBase(String name) throws ConnectionNotInitException {
        super(name);
    }

    public void refreshSongs(){
        String[] files = Settings.getInstance().get("dirs").split(";");
        DAO<Song> songDAO = DAO.construct(Song.class);
        for (String file : files){
            LocalDateTime now = LocalDateTime.now();
            DiskFileExplorer dfe = new DiskFileExplorer(file, true);
            List<Song> songs = songDAO.getList();
            List<String> paths = dfe.list();

            for (Song song : songs){
                String preURL = song.getLocalurl();
                String URL = preURL.replace(file, "").substring(1);
                if (!preURL.contains(URL) && !paths.contains(URL)) songDAO.delete(song); //If song on database but not on disk
                else paths.remove(URL);
            }
            for (String path : paths){
                Song temp;
                if (path.contains(sep)){
                    String title = path.substring(path.indexOf(sep)+sep.length()).replace("_"," ");
                    String artist = path.substring(0,path.indexOf(sep)).replace("_"," ");
                    temp = new Song(-1, title.substring(0,title.length()-4), artist, file+"\\"+path,"");
                } else
                    temp = new Song(-1, path.substring(0,path.length()-4), "unknown", file+"\\"+path,"");
                songDAO.create(temp);

            }
            LocalDateTime then = LocalDateTime.now();
            System.out.println("Loading songs : "+ ChronoUnit.SECONDS.between(now, then)+"s");
            System.out.println(songDAO.getList().size());
        }
    }
}
