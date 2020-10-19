package com.frosqh.paikeaserver.locale;


@SuppressWarnings("SpellCheckingInspection")
public class FRFR implements Locale {
    @Override
    public String paikeSong() {
        return  "\n" +
                "            Uia mai koia, whakahuatia ake;\n"+
                "            Ko wai te whare nei e?\n" +
                "            Ko Te Kani / Ko Rangi / Whitireia!\n" +
                "            Ko wai te tekoteko kei runga?\n" +
                "            Ko Paikea! Ko Paikea!\n" +
                "            Whakakau Paikea. Hei!\n" +
                "            Whakakau he tipua. Hei!\n" +
                "            Whakakau he taniwha. Hei!\n" +
                "            Ka ū Paikea ki Ahuahu. Pakia!\n" +
                "            Kei te whitia koe\n" +
                "            ko Kahutia-te-rangi. Aue!\n" +
                "            Me ai tō ure ki te tamahine\n" +
                "            a Te Whironui - aue!\n" +
                "            nāna i noho te Roto-o-tahe.\n" +
                "            Aue! Aue!\n" +
                "            He koruru koe, koro e.";
    }

    @Override
    public String welcomeMessage() {
        return "Plop, je suis en ligne ! Tente !help pour avoir la liste des commandes disponibles. \n" +
                "Si tu veux accéder à l'interface en ligne, va sur : http://bot.paikea.wtf";
    }

    @Override
    public String list() {
        return "Voici la liste des commandes disponibles : \n";
    }

    @Override
    public String seeMore() {
        return "N'hésite pas à taper !help [cmd] pour obtenir de l'aide spécifique à une commande";
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
    public String deschelp() {
        return "Affiche ce message d'aide";
    }

    @Override
    public String descpaikea() {
        return "Surprise !";
    }

    @Override
    public String descnext() {
        return "Lit la prochaine musique dans la liste d'attente";
    }

    @Override
    public String descplay() {
        return "Sort le lecteur de l'état 'Pause'";
    }

    @Override
    public String descpause() {
        return "Met le lecteur dans l'état 'Pause'";
    }

    @Override
    public String descprev() {
        return  "Revient à la dernière musique jouée";
    }

    @Override
    public String descsetvolume() {
        return "Change le volume du lecteur";
    }

    @Override
    public String desctoggleautoplay() {
        return "(Dés)Active la lecture automatique";
    }

    @Override
    public String descinfo() {
        return "Affiche la musique en cours";
    }

    @Override
    public String descchallenge() {
        return "Défie l'utilisateur passé en paramètre au chifoumi";
    }

    @Override
    public String descaddgame() {
        return "Ajoute un jeu disponible à la base de données";
    }

    @Override
    public String descgamelist() {
        return "Affiche la liste des différents jeux disponibles";
    }

    @Override
    public String descrandomgame() {
        return "Affiche un jeu au hasard parmi la liste des jeux renseignés";
    }

    @Override
    public String desctoggledm() {
        return "(Dés)Active le mode MJ. Plus de détails sur le mode MJ peuvent être dans l'aide en ligne";
    }

    @Override
    public String nowPlaying(String song, String artist) {
        return  "♫ Now playing - "+song+" by " + artist + " ♫";
    }

    @Override
    public String nothingPlaying() {
        return "Aucune musique n'est en train d'être jouée.";
    }

    @Override
    public String succPlay() {
        return "La lecture a bien été (re)prise !";
    }

    @Override
    public String toggleAutoPlayOn() {
        return "La lecture automatique est désormais active !";
    }

    @Override
    public String toggleAutoPlayOff() {
        return "La lecture automatique est désactivée";
    }

    @Override
    public String challengeAnnounce(String challenger, String challengee) {
        return "Bonjour "+challengee+", "+challenger+" te défie à une partie de chifoumi. Réponds moi dans le chat privé pour participer.";
    }

    @Override
    public String challengeChoice() {
        return "Que veux-tu jouer ? \n Papier : 1 \n Pierre : 2 \n Ciseaux : 3";
    }

    @Override
    public String challengeAsking() {
        return "Veux-tu accepter le défi chifoumi ? (y/n)";
    }

    @Override
    public String challengeAbort() {
        return "L'autre participant n'a malheureusement pas accepté le challenge.";
    }

    @Override
    public String gamelist() {
        return "Liste des jeux disponibles : \n";
    }

    @Override
    public String chosengame() {
        return "Le jeu choisi est ";
    }

    @Override
    public String gameadded(String name) {
        return String.format(" Le jeu %s a bien été ajouté à la BDD",name);
    }

    @Override
    public String dmModeEnabled() {
        return "Le mode MJ a bien été activé. S'il te plaît, souviens-toi du mot de passe que tu as mis ...";
    }

    @Override
    public String dmModeDisabled() {
        return "Mot de passe correct, le mode MJ a bien été désactivé.";
    }

    @Override
    public String dmModePasswordIncorrect() {
        return "Mot de passe incorrect, le mode MJ n'a pas été désactivé.";
    }

    @Override
    public String didYouMean(String arg, String almost) {
        return  "Désolé, mais la commande "+arg+" n'existe pas. Vouliez-vous dire "+almost+" ?";
    }

    @Override
    public String triedToConnect(int id, String name, String cmd) {
        return String.format("Attention, l'utilisateur %d (%s) a tenté de taper une commande : %s", id, name, cmd);
    }

    @Override
    public String hiddenSong() {
        return "Chanson cachée";
    }

    @Override
    public String errorPause() {
        return  "Rien n'est est en train d'être joué, réfléchis un peu ><";
    }

    @Override
    public String errorOnPlay() {
        return "La lecture n'a pas pu être reprise, n'hésite pas à réessayer si cela te semble étrange.";
    }

    @Override
    public String errorPlay() {
        return "Ça joue déjà, réfléchis un peu ><";
    }

    @Override
    public String errorOnAddGame(String toAdd) {
        return String.format("Oops, pour une raison obscure, on n'a pas pu ajouter %s à la BDD",toAdd);
    }

    @Override
    public String notFound(String cmd) {
        return "Commande "+cmd+" non trouvée";
    }

    @Override
    public String undefinedBehavior() {
        return "Alors, crois le ou non, mais il semblerait qu'on est oublié de définir le fonctionnement de cette commande 0:)";
    }

    @Override
    public String easterUndefinedBehavior() {
        return "Ah, c'est un peu bête, je suis sûr qu'on avait pensé à un easter egg ici, mais on a du oublier :/";
    }

    @Override
    public String dmModeAlreadyEnabled() {
        return "Le mode MJ est déjà activé. Il faut le désactiver puis le réactiver si besoin est.";
    }

    @Override
    public String wip() {
        return "Cette fonction est encore en cours de développement, elle peut ne pas être fonctionnelle";
    }

    @Override
    public String noPrev() {
        return "Désolé, mais aucune chanson n'a précédé celle-ci";
    }

    @Override
    public String notAuthorized(String name) {
        return "You are not authorized to use this command in the mode "+name;
    }

    @Override
    public String easterShit() {
        return "merde";
    }

    @Override
    public String easterShitResponse() {
        return "Diantre*";
    }

    @Override
    public String easterGoogleResponse() {
        return "Nan mais oh, tu me prends pour qui là ? ><";
    }

    @Override
    public String easterNoResponse() {
        return "Si.";
    }

    @Override
    public String easterPlopResponse() {
        return "Plop à toi, mon frère !";
    }

    @Override
    public String emptyHistory() {
        return "L'historique ne contient pas de chansons";
    }

    @Override
    public String pauseException() {
        return "Le player n'est pas initialisé ou aucune musique n'est en cours de lecture";
    }

    @Override
    public String playException() {
        return "Le player n'est pas initialisé ou une musique est déjà en cours de lecture";
    }

    @Override
    public String usageaddgame() {
        return "Usage : !addgame [nomdujeu]";
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
        return "Usage : !toggleDM [motdepasse]";
    }
}
