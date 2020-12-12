package com.frosqh.paikeaserver.ts3api;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.Game;
import com.frosqh.paikeaserver.downloader.Downloader;
import com.frosqh.paikeaserver.locale.Locale;
import com.frosqh.paikeaserver.player.PlayMode;
import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.player.exceptions.EmptyHistoryException;
import com.frosqh.paikeaserver.player.exceptions.PauseException;
import com.frosqh.paikeaserver.player.exceptions.PlayException;
import com.frosqh.paikeaserver.ts3api.exception.NotACommandException;
import com.frosqh.paikeaserver.ts3api.spellchecker.LevenshteinDistance;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private final String[] baseCommands;
    private final String[] easterEggs;
    private final String[] complexCommands;
    private final Locale locale;
    private final Player player;
    private final Ts3Api ts3Api;

    public CommandManager(Locale locale, Player player, Ts3Api ts3Api){
        CommandHistory commandHistory = new CommandHistory();
        this.locale = locale;
        this.player = player;
        this.ts3Api = ts3Api;
        baseCommands = new String[]{"paikea", "next", "play", "pause", "prev", "toggleautoplay", "info", "gamelist","randomgame"};
        //TODO add invoke
        easterEggs =  new String[]{this.locale.easterShit(),"ok google", "><", "nan", "no", "nope", "non", "nan", "niet", "nein", "pong", "ping", "plop"};
        complexCommands = new String[]{"help", "setvolume", "addgame", "toggledm", "download"};
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

    @SuppressWarnings("unchecked")
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
                        rep = new StringBuilder(player.getInfos());
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

    public String execComplex(String command, int callerID) {
        try {
            String cmd = preProcess(command);
            String[]args = cmd.split(" ");
            switch (args[0].toLowerCase()) {
                case "help" -> {
                    String toDetail = args[1];
                    if (isHelpable("!" + toDetail)) return getUsage(toDetail);
                    else return locale.notFound(toDetail);
                }
                case "setvolume" -> {
                    if (args.length != 2)
                        return locale.usagesetvolume();
                    player.setVolume(Double.parseDouble(args[1]));
                    return player.getInfos();
                }
                case "addgame" -> {
                    if (args.length != 2)
                        return locale.usageaddgame();
                    String toAdd = args[1];
                    DAO<Game> gameDAO = DAO.construct(Game.class);
                    Game game = gameDAO.create(new Game(0, toAdd));
                    if (game != null)
                        return locale.gameadded(game.name);
                    else
                        return locale.errorOnAddGame(toAdd);
                }
                case "toggledm" -> {
                    if (args.length != 2)
                        return locale.usagetoggledm();
                    String password = args[1];
                    PlayMode mode = player.getMode();
                    if (mode == PlayMode.DM) {
                        if (mode.isPasswordValid(password)) {
                            player.setPlayMode(PlayMode.NORMAL);
                            return locale.dmModeDisabled();
                        } else {
                            return locale.dmModePasswordIncorrect();
                        }
                    } else {
                        if (mode == PlayMode.NORMAL) {
                            player.setPlayMode(PlayMode.DM);
                            player.getMode().setPassword(password);
                            player.getMode().setUid(callerID);
                            return locale.dmModeEnabled();
                        }
                    }
                    return locale.undefinedBehavior();
                }
                case "download" -> {
                    List<String> li = Arrays.asList(args).subList(1, args.length);
                    String[] args2 = new String[6];
                    int prec = -1;
                    for (String s : li){
                        if ("-i".equals(s)) {
                            args2[0] = s;
                            prec = 1;
                            args2[1] = "";
                        }
                        else if ("-t".equals(s)){
                            args2[2] = s;
                            prec = 2;
                            args2[3] = "";
                        }
                        else if ("-a".equals(s)){
                            args2[4]=s;
                            prec = 3;
                            args2[5] = "";
                        } else {
                            args2[prec * 2 - 1] += s + " ";
                        }

                    }
                    for (int i = 0; i<=2; i++) {
                        args2[2 * i + 1] = args2[2 * i + 1].substring(0, args2[2 * i + 1].length() - 1);
                    }
                    System.out.println(Arrays.toString(args2));
                    ArgumentParser argumentParser = ArgumentParsers.newArgumentParser("download")
                            .description("Allow for downloads");
                    argumentParser.addArgument("-i","-id")
                            .nargs(1);
                    argumentParser.addArgument("-t","-title")
                            .nargs(1);
                    argumentParser.addArgument("-a","-artist")
                            .nargs(1);
                    Namespace ns = argumentParser.parseArgs(args2);
                    System.out.println(ns);
                    String id = (String) ns.getList("i").get(0);
                    if (id.startsWith(" ")){
                        id = id.substring(1);
                    }
                    Downloader.getInstance().downloadFromYoutube(id,
                            (String) ns.getList("t").get(0),
                            (String) ns.getList("a").get(0));

                }
            }
        } catch (NotACommandException ignored) {
            return "‼"; //Should not happen and be checked before !
        } catch (ArgumentParserException e) {
            e.printStackTrace();
            return locale.usagedownload();
        }
        return "Complex !";
    }


}
