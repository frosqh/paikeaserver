package com.frosqh.paikeaserver.locale;

@SuppressWarnings("ALL")
public interface Locale{

    String paikeSong();

    String welcomeMessage();

    String list();

    String seeMore();

    // Commands Usage

    String usagehelp();
    String usagepaikea();
    String usagenext();
    String usageplay();
    String usagepause();
    String usageprev();
    String usagesetvolume();
    String usagetoggleautoplay();
    String usageinfo();
    String usagechallenge();
    String usageaddgame();
    String usagegamelist();
    String usagerandomgame();
    String usagetoggledm();

    // Commands Help

    String deschelp();
    String descpaikea();
    String descnext();
    String descplay();
    String descpause();
    String descprev();
    String descsetvolume();
    String desctoggleautoplay();
    String descinfo();
    String descchallenge();
    String descaddgame();
    String descgamelist();
    String descrandomgame();
    String desctoggledm();

    // Commands Results

    String nowPlaying(String song, String artist);
    String nothingPlaying();
    String succPlay();
    String toggleAutoPlayOn();
    String toggleAutoPlayOff();
    String challengeAnnounce(String challenger, String challengee);
    String challengeChoice();
    String challengeAsking();
    String challengeAbort();
    String gamelist();
    String chosengame();
    String gameadded(String name);
    String dmModeEnabled();
    String dmModeDisabled();
    String dmModePasswordIncorrect();

    // Other

    String didYouMean(String arg, String almost);
    String triedToConnect(int id, String name, String cmd);

    //Errors

    String errorPause();
    String errorOnPlay();
    String errorPlay();
    String errorOnAddGame(String toAdd);
    String notFound(String cmd);
    String undefinedBehavior();
    String easterUndefinedBehavior();
    String dmModeAlreadyEnabled();
    String wip();
    String noPrev();
    String notAuthorized(String name);

    // Easters input

    String easterShit();

    //Easters output

    String easterShitResponse();
    String easterGoogleResponse();
    String easterNoResponse();
    String easterPlopResponse();

    //Exceptions
    String emptyHistory();

    String pauseException();

    String playException();



}
