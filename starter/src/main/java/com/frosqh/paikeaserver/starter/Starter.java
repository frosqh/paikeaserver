package com.frosqh.paikeaserver.starter;

import com.frosqh.daolibrary.ConnectionSQLite;
import com.frosqh.paikeaserver.database.*;
import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.settings.Settings;
import com.frosqh.paikeaserver.ts3api.Ts3Api;

import java.util.Arrays;

public class Starter {

    public static Player player;

    public static void  start(String[] args) throws Exception {
        com.sun.javafx.application.PlatformImpl.startup(Thread.currentThread());
        Settings.getInstance().load();
        ConnectionSQLite.init(Settings.get("database"));
        PaikeaDataBase.models.add(Song.class);
        PaikeaDataBase.models.add(PlayList.class);
        PaikeaDataBase.models.add(SongByPlayList.class);
        PaikeaDataBase dataBase = new PaikeaDataBase(Settings.get("database"));
        dataBase.refreshSongs();
        player = new Player(Settings.getInstance().getLocale());
        new Ts3Api(Settings.getInstance().getProperty("sv_address"),Settings.get("sv_login"),
                Settings.get("sv_password"),Settings.get("bot_name"),
                Arrays.asList(Settings.get("known_users").split(";")),
                Settings.getLocale(),
                player);
        player.next();
        //TODO cancel this one out
    }
}
