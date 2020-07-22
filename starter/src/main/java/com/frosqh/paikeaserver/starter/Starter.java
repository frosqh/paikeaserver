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
        ConnectionSQLite.init(Settings.getInstance().get("database"));
        PaikeaDataBase.models.add(Song.class);
        PaikeaDataBase.models.add(Game.class);
        PaikeaDataBase.models.add(User.class);
        PaikeaDataBase.models.add(GoogleAccount.class);
        PaikeaDataBase.models.add(MailAccount.class);
        PaikeaDataBase.models.add(TeamspeakAccount.class);
        PaikeaDataBase.models.add(FacebookAccount.class);
        PaikeaDataBase.models.add(PlayList.class);
        PaikeaDataBase.models.add(SongByPlayList.class);
        PaikeaDataBase dataBase = new PaikeaDataBase(Settings.getInstance().get("database"));
        dataBase.refreshSongs();
        player = new Player(Settings.getInstance().getLocale());
        new Ts3Api(Settings.getInstance().get("sv_address"),Settings.getInstance().get("sv_login"),
                Settings.getInstance().get("sv_password"),Settings.getInstance().get("bot_name"),
                Arrays.asList(Settings.getInstance().get("known_users").split(";")),
                Settings.getInstance().getLocale(),
                player);
        player.next();
        //TODO cancel this one out
    }
}
