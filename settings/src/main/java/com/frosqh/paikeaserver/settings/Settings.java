package com.frosqh.paikeaserver.settings;

import com.frosqh.paikeaserver.locale.FRFR;
import com.frosqh.paikeaserver.locale.Locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Settings extends Properties {

    private Locale locale;
    private final static String[] keyword = {"database", "dirs", "port", "sv_address", "sv_login", "sv_password",
            "socket_port", "dirDL", "locale"};
    private final static String[] intExp = {"port", "socket_port"};

    private static Settings instance;

    public static Settings getInstance(){
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    private void createSettings() throws IOException {
        File file = new File("../server.properties");
        FileOutputStream output = new FileOutputStream(file);
        setProperty("database", "BotPaikea.db");
        setProperty("dirs", "C:\\Users\\Admin\\Music");
        setProperty("port", "2302");
        setProperty("bot_name", "Bot Paikea");
        setProperty("sv_address", "127.0.0.1");
        setProperty("sv_login", "login_query");
        setProperty("sv_password", "password_query");
        setProperty("known_users", "john; james");
        setProperty("socket_port", "8080");
        setProperty("locale", "fr_fr");
        setProperty("dirDL", "C:\\Users\\Admin\\Music\\Downloads");
        store(output, "Bot Paikea Server Properties");
        output.close();
    }

    public void load() throws Exception {
        File file = new File("../server.properties");
        if (!file.exists()) {
            createSettings();
            System.exit(2);
        }
        FileInputStream input = new FileInputStream(file);
        load(input);
        String missings = checkSettingsIntegrity();
        if (missings != null)
            throw new Exception(missings);
        for (String key: intExp)
            if (!isInteger(getProperty(key)))
                throw new Exception("Integer excepted for key "+key);
        //noinspection SwitchStatementWithTooFewBranches
        switch (getProperty("locale")) {
            case "fr_fr" -> locale = new FRFR();
            default -> {
                System.out.println("No locale specified, fr_fr chosen");
                locale = new FRFR(); // TODO ENEN;
            }
        }
        File dirDL = new File(getProperty("dirDL"));
        System.out.println(dirDL);
        if (!dirDL.exists())
            if (!dirDL.mkdir())
                throw new Exception("Download directory does not exist");
    }

    private boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private String checkSettingsIntegrity(){
        List<String> errs = new ArrayList<>();
        for (String key : keyword){
            if (!keySet().contains(key))
                errs.add(key);
        }
        StringBuilder res = new StringBuilder();
        for (String e : errs)
            res.append(e).append(", ");
        if (res.length()>0)
            return res.substring(0,res.length()-2);
        return null;
    }

    public static Locale getLocale() {
        return getInstance().locale;
    }

    public static String get(String key){
        return getInstance().getProperty(key);
    }



}
