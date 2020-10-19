package com.frosqh.paikeaserver.settings;

import com.frosqh.paikeaserver.locale.FRFR;
import com.frosqh.paikeaserver.locale.Locale;

import java.io.*;
import java.util.*;

// TODO : Make the transition !
public class SettingsOld extends HashMap<String, String> {
    private Locale locale;
    private final static String[] keywords = {"database","dirs","port","sv_address","sv_login","sv_password","socket_port"};
    private final static String[] intExp = {"port","socket_port"};

    private static final String properties =
            "#Bot Paikea Server Properties\n" +
                    "database=BotPaikea.db\n" +
                    "dirs=C:\\Users\\Admin\\Music\n"+
                    "port=2302\n"+
                    "bot_name=Bot Paikea\n"+
                    "sv_address=127.0.0.1\n"+
                    "sv_login=login_query\n"+
                    "sv_password=password_query\n"+
                    "known_users=john;james\n"+
                    "socket_port=8080";

    private static SettingsOld instance;

    public static SettingsOld getInstance(){
        if (instance == null)
            instance = new SettingsOld();
        return instance;
    }

    private void createSettings() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./server.properties"))) {
            bufferedWriter.write(properties);
            bufferedWriter.close();
            System.err.println("Settings à compléter");
            System.exit(1);
        }
    }

    public void load() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./server.properties"))){
            String line;
            while ((line = bufferedReader.readLine())!=null){
                if (!line.startsWith("#")){
                    String[] prop = line.split("=");
                    String key = prop[0];
                    String value = prop[1];
                    if (Arrays.asList(intExp).contains(key) && !isInteger(value))
                        throw new Exception("Integer expected");
                    if (key.equals("locale")){
                        //noinspection SwitchStatementWithTooFewBranches
                        switch (value){
                            case "fr_fr":
                                locale = new FRFR();
                                break;
                            default:
                                System.out.println("No locale specified, fr_fr chosen");
                                locale = new FRFR(); //TODO ENEN
                        }
                    } else put(key,value);
                }
            }
        } catch (FileNotFoundException e) {
            createSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String missings = checkSettingsIntegrity();
        if (missings != null)
            throw new Exception(missings);
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
        //Set<String> keySet = keySet();
        List<String> errs = new ArrayList<>();
        for (String key : keywords)
            if (!keySet().contains(key))
                errs.add(key);
        StringBuilder res = new StringBuilder();
        for (String e : errs)
            res.append(e).append(", ");
        if (res.length()>0)
            return res.substring(0,res.length()-2);
        return null;
    }

    public Locale getLocale() {
        return locale;
    }
}
