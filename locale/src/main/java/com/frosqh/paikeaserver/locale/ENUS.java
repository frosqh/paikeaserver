package com.frosqh.paikeaserver.locale;

public class ENUS implements Locale {
    @Override
    public String paikeSong() {
        return """

                Uia mai koia, whakahuatia ake;
                Ko wai te whare nei e?
                Ko Te Kani / Ko Rangi / Whitireia!
                Ko wai te tekoteko kei runga?
                Ko Paikea! Ko Paikea!
                Whakakau Paikea. Hei!
                Whakakau he tipua. Hei!
                Whakakau he taniwha. Hei!
                Ka ū Paikea ki Ahuahu. Pakia!
                Kei te whitia koe
                ko Kahutia-te-rangi. Aue!
                Me ai tō ure ki te tamahine
                a Te Whironui - aue!
                nāna i noho te Roto-o-tahe.
                Aue! Aue!
                He koruru koe, koro e.""".indent(12);
    }

    @Override
    public String welcomeMessage() {
        return "Hey, I'm online ! Try !help to get the list of available commands. :whale2: \n" +
                "If you want to use the online tool instead, go to : http://bot.paikea.wtf";
    }

    @Override
    public String list() {
        return "Here is the list of available commands : \n";
    }

    @Override
    public String seeMore() {
        return "Try !help [cmd] to get further help on a command";
    }

    @Override
    public String usagehelp() {
        return "Usage : !help [cmd]";
    }

    @Override
    public String usagepaikea() {
        return "Usage : !paikea";
    }

    @Override
    public String usagenext() {
        return "Usage : !next";
    }

    @Override
    public String usageplay() {
        return "Usage : !play";
    }

    @Override
    public String usagepause() {
        return "Usage : !pause";
    }

    @Override
    public String usageprev() {
        return "Usage : !prev";
    }

    @Override
    public String usagesetvolume() {
        return "Usage : !setVolume [volume]";
    }

    @Override
    public String usagetoggleautoplay() {
        return "Usage : !toggleAutoPlay";
    }

    @Override
    public String usageinfo() {
        return "Usage : !info";
    }

    @Override
    public String usagechallenge() {
        return "Usage : !challenge [user]";
    }

    @Override
    public String usageaddgame() {
        return "Usage : !addgame [gamename]";
    }

    @Override
    public String usagegamelist() {
        return "Usage : !gamelist";
    }

    @Override
    public String usagerandomgame() {
        return "Usage : !randomgame";
    }

    @Override
    public String usagetoggledm() {
        return "Usage : !toggleDM [password]";
    }

    @Override
    public String deschelp() {
        return "Show this help message";
    }

    @Override
    public String descpaikea() {
        return "Surprise ! :gift:";
    }

    @Override
    public String descnext() {
        return "Play next music in play list :hourglass_flowing_sand:";
    }

    @Override
    public String descplay() {
        return "Exit the 'Pause' state";
    }

    @Override
    public String descpause() {
        return "Enter the 'Pause' state";
    }

    @Override
    public String descprev() {
        return "Get back to previous played music";
    }

    @Override
    public String descsetvolume() {
        return "Set a new player's volume :loud_sound:";
    }

    @Override
    public String desctoggleautoplay() {
        return "(Dis)able auto play :twisted_rightwards_arrows:";
    }

    @Override
    public String descinfo() {
        return "Display information about the playing song";
    }

    @Override
    public String descchallenge() {
        return "TODO";
    }
    //TODO !!
    @Override
    public String descaddgame() {
        return null;
    }

    @Override
    public String descgamelist() {
        return null;
    }

    @Override
    public String descrandomgame() {
        return null;
    }

    @Override
    public String desctoggledm() {
        return null;
    }

    @Override
    public String nowPlaying(String song, String artist) {
        return null;
    }

    @Override
    public String nothingPlaying() {
        return null;
    }

    @Override
    public String succPlay() {
        return null;
    }

    @Override
    public String toggleAutoPlayOn() {
        return null;
    }

    @Override
    public String toggleAutoPlayOff() {
        return null;
    }

    @Override
    public String challengeAnnounce(String challenger, String challengee) {
        return null;
    }

    @Override
    public String challengeChoice() {
        return null;
    }

    @Override
    public String challengeAsking() {
        return null;
    }

    @Override
    public String challengeAbort() {
        return null;
    }

    @Override
    public String gamelist() {
        return null;
    }

    @Override
    public String chosengame() {
        return null;
    }

    @Override
    public String gameadded(String name) {
        return null;
    }

    @Override
    public String dmModeEnabled() {
        return null;
    }

    @Override
    public String dmModeDisabled() {
        return null;
    }

    @Override
    public String dmModePasswordIncorrect() {
        return null;
    }

    @Override
    public String didYouMean(String arg, String almost) {
        return null;
    }

    @Override
    public String triedToConnect(int id, String name, String cmd) {
        return null;
    }

    @Override
    public String hiddenSong() {
        return null;
    }

    @Override
    public String errorPause() {
        return null;
    }

    @Override
    public String errorOnPlay() {
        return null;
    }

    @Override
    public String errorPlay() {
        return null;
    }

    @Override
    public String errorOnAddGame(String toAdd) {
        return null;
    }

    @Override
    public String notFound(String cmd) {
        return null;
    }

    @Override
    public String undefinedBehavior() {
        return null;
    }

    @Override
    public String easterUndefinedBehavior() {
        return null;
    }

    @Override
    public String dmModeAlreadyEnabled() {
        return null;
    }

    @Override
    public String wip() {
        return null;
    }

    @Override
    public String noPrev() {
        return null;
    }

    @Override
    public String notAuthorized(String name) {
        return null;
    }

    @Override
    public String easterShit() {
        return null;
    }

    @Override
    public String easterShitResponse() {
        return null;
    }

    @Override
    public String easterGoogleResponse() {
        return null;
    }

    @Override
    public String easterNoResponse() {
        return null;
    }

    @Override
    public String easterPlopResponse() {
        return null;
    }

    @Override
    public String emptyHistory() {
        return null;
    }

    @Override
    public String pauseException() {
        return null;
    }

    @Override
    public String playException() {
        return null;
    }

    @Override
    public String usagedownload() {
        return null;
    }
}
