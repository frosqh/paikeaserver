package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

public class Song extends Model {

    public String title;
    public String artist;
    public String localurl;
    public String weburl;

    public Song(int id) {
        super(id);
    }

    public Song(int id, String title, String artist, String localurl, String weburl){
        super(id);
        this.title = title;
        this.artist = artist;
        this.localurl = localurl;
        this.weburl = weburl;
    }

    @Override
    public String toString(){
        return artist+" - "+title;
    }

    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getLocalurl() {
        return localurl;
    }
    public String getWeburl() {
        return weburl;
    }

    public void setTitle(String substring) {
        title = substring;
    }
}
