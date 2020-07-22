package com.frosqh.paikeaserver.ts3api;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.Game;
import com.frosqh.paikeaserver.locale.Locale;
import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.player.exceptions.EmptyHistoryException;
import com.frosqh.paikeaserver.player.exceptions.PauseException;
import com.frosqh.paikeaserver.player.exceptions.PlayException;
import com.frosqh.paikeaserver.ts3api.exception.NotACommandException;
import com.frosqh.paikeaserver.ts3api.spellchecker.LevenshteinDistance;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private final String[] baseCommands;
    private final String[] easterEggs;
    private final String[] complexCommands;
    private final Locale locale;
    private final CommandHistory commandHistory;
    private final Player player;
    private final Ts3Api parent;

    public CommandManager(Locale locale, Player player, Ts3Api ts3Api){
        this.commandHistory = new CommandHistory();
        this.locale = locale;
        this.player = player;
        this.parent = ts3Api;
        baseCommands = new String[]{"paikea", "next", "play", "pause", "prev", "toggleautoplay", "info", "gamelist","randomgame"};
        //TODO add invoke
        easterEggs =  new String[]{this.locale.easterShit(),"ok google", "><", "nan", "no", "nope", "non", "nan", "niet", "nein", "pong", "ping", "plop"};
        complexCommands = new String[]{"help", "setvolume", "addgame"};
    }

    private String preProcess(String cmd) throws NotACommandException {
        if (cmd.charAt(0)!='!')
            throw new NotACommandException();
        return cmd.substring(1);
    }

    public boolean isBase(String cmd) throws NotACommandException {
        cmd = preProcess(cmd);
        return Arrays.asList(baseCommands).contains(cmd);
    }

    public boolean isEaster(String cmd){
        return Arrays.asList(easterEggs).contains(cmd);
    }

    public boolean isComplex(String cmd) throws NotACommandException {
        cmd = preProcess(cmd);
        return Arrays.asList(complexCommands).contains(cmd);
    }

    private boolean isHelpable(String cmd) throws NotACommandException {
        return isBase(cmd) || isComplex(cmd);
    }

    public String isAlmostACommand(String cmd) throws NotACommandException {
        cmd = preProcess(cmd);
        for (String base : baseCommands)
            if (LevenshteinDistance.getDistance(base, cmd)<2)
                return base;
        for (String complex : complexCommands)
            if (LevenshteinDistance.getDistance(complex, cmd)<2)
                return complex;
        return null;
    }

    private String getUsage(String cmd){
        try {
            return locale.getClass().getDeclaredMethod("usage"+cmd.toLowerCase(), (Class<?>[])null).invoke(locale, (Object[]) null)
                    + "\n"
                    + locale.getClass().getDeclaredMethod("desc"+cmd.toLowerCase(), (Class<?>[])null).invoke(locale, (Object[]) null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String execEaster(String cmd){
        switch(cmd){
            case "ok google":
                return locale.easterGoogleResponse();
            case "><":
                return ":D";
            case "nan":
            case "nope":
            case "no":
            case "niet":
            case "non":
            case "nein":
                return locale.easterNoResponse();
            case "pong":
                return "Ping ?";
            case "ping":
                return "Pong !";
            case "plop":
                return locale.easterPlopResponse();
            default:
                if (locale.easterShit().equals(cmd))
                    return locale.easterShitResponse();
                return locale.easterUndefinedBehavior();
        }
    }

    public String execBase(String command){
        StringBuilder rep = new StringBuilder("default");
        try {
            String cmd = preProcess(command);
            switch (cmd){
                case "help":
                    rep = new StringBuilder(locale.list());
                    for (String com : baseCommands){
                        String desc = (String) locale.getClass().getDeclaredMethod("desc"+com.toLowerCase(), (Class<?>[]) null).invoke(locale, (Object[]) null);
                        rep.append("\t\t• !").append(com).append(" : ").append(desc).append("\n");
                    }
                    for (String com : complexCommands){
                        String desc = (String) locale.getClass().getDeclaredMethod("desc"+com.toLowerCase(), (Class<?>[]) null).invoke(locale, (Object[]) null);
                        rep.append("\t\t• !").append(com).append(" : ").append(desc).append("\n");
                    }
                    rep.append(locale.seeMore());
                    break;
                case "paikea":
                    rep = new StringBuilder(locale.paikeSong());
                    break;
                case "next":
                    player.next();
                    rep = new StringBuilder(locale.nowPlaying(player.getPlaying().getTitle(), player.getPlaying().getArtist()));
                    break;
                case "prev":
                    try {
                        player.prev();
                        rep = new StringBuilder(locale.nowPlaying(player.getPlaying().getTitle(), player.getPlaying().getArtist()));
                    } catch (EmptyHistoryException e) {
                        rep = new StringBuilder(locale.noPrev());
                    }

                    break;
                case "pause":
                    try {
                        player.pause();
                        rep = null;
                    } catch (PauseException e) {
                        rep = new StringBuilder(locale.errorPause());
                    }
                    break;
                case "play":
                    try {
                        player.play();
                        rep = new StringBuilder(locale.nowPlaying(player.getPlaying().getTitle(), player.getPlaying().getArtist()));
                    } catch (PlayException e) {
                        rep = new StringBuilder(locale.errorPlay());
                    }
                    break;
                case "info":
                    rep = new StringBuilder(player.getInfos());
                    break;
                case "gamelist":
                    List<Game> l = DAO.construct(Game.class).getList();
                    rep = new StringBuilder(locale.gamelist());
                    for (Game g : l){
                        rep.append(g.name).append("\n");
                    }
                    break;
                case "randomgame":
                    l = DAO.construct(Game.class).getList();
                    rep = new StringBuilder(locale.chosengame());
                    rep.append(l.get((int) (Math.random()*(l.size()))).name);
                    break;

                default:
                    rep = new StringBuilder(locale.undefinedBehavior());
                    break;
            }
        } catch (NotACommandException ignored) {
            return "‼"; //Should not happen and be checked before !
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (rep != null)
            return rep.toString();
        return "";
    }

    public String execComplex(String command, String caller) {
        try {
            String cmd = preProcess(command);
            String[]args = cmd.split(" ");
            switch (args[0]){
                case "help":
                    String toDetail = args[1];
                    if (isHelpable("!"+toDetail)) return getUsage(toDetail);
                    else return locale.notFound(toDetail);
                case "setvolume":
                    if (args.length != 2)
                        return locale.usagesetvolume();
                    player.setVolume(Double.parseDouble(args[1]));
                    return player.getInfos();
                case "addgame":
                    if (args.length != 2)
                        return locale.usageaddgame();
                    String toAdd = args[1];
                    DAO<Game> gameDAO = DAO.construct(Game.class);
                    Game game = gameDAO.create(new Game(0, toAdd));
                    if (game!=null)
                        return locale.gameadded(game.name);
                    else
                        return locale.errorOnAddGame(toAdd);


            }
        } catch (NotACommandException ignored) {
            return "‼"; //Should not happen and be checked before !
        }
        return "Complex !";
    }


}
