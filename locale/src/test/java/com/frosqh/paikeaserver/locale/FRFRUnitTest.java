package com.frosqh.paikeaserver.locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("SpellCheckingInspection")
public class FRFRUnitTest {

    private FRFR fr;

    @Before
    public void defFr(){
        fr = new FRFR();
    }

    @Test
    public void welcomeTest(){
        Assert.assertEquals("Plop, je suis en ligne ! Tente !help pour avoir la liste des commandes disponibles.",fr.welcomeMessage());
    }

    @Test
    public void listTest(){
        Assert.assertEquals("Voici la liste des commandes disponibles : \n",fr.list());
    }

    @Test
    public void seeMoreTest(){
        Assert.assertEquals("N'hésite pas à taper !help [cmd] pour obtenir de l'aide spécifique à une commande",fr.seeMore());
    }

    @Test
    public void usageHelpTest(){
        Assert.assertEquals("Usage : !help [cmd]",fr.usagehelp());
    }

    @Test
    public void usageNextTest(){
        Assert.assertEquals("Usage : !next",fr.usagenext());
    }

    @Test
    public void usagePlayTest(){
        Assert.assertEquals("Usage : !play",fr.usageplay());
    }

    @Test
    public void usagePauseTest(){
        Assert.assertEquals("Usage : !pause",fr.usagepause());
    }

    @Test
    public void usagePrevTest(){
        Assert.assertEquals("Usage : !prev",fr.usageprev());
    }

    @Test
    public void usageSetVolumeTest(){
        Assert.assertEquals("Usage : !setVolume [volume]",fr.usagesetvolume());
    }

    @Test
    public void descHelpTest(){
        Assert.assertEquals("Affiche ce message d'aide", fr.deschelp());
    }

    @Test
    public void descPaikeaTest(){
        Assert.assertEquals("Surprise !",fr.descpaikea());
    }

    @Test
    public void descNextTest(){
        Assert.assertEquals("Lit la prochaine musique dans la liste d'attente",fr.descnext());
    }

    @Test
    public void descPlayTest(){
        Assert.assertEquals("Sort le lecteur de l'état 'Pause'",fr.descplay());
    }

    @Test
    public void descPauseTest(){
        Assert.assertEquals("Met le lecteur dans l'état 'Pause'", fr.descpause());
    }

    @Test
    public void descPrevTest(){
        Assert.assertEquals("Revient à la dernière musique jouée",fr.descprev());
    }

    @Test
    public void descSetVolumeTest(){
        Assert.assertEquals("Change le volume du lecteur", fr.descsetvolume());
    }

    @Test
    public void descToggleAutoPlayTest(){
        Assert.assertEquals("(Dés)Active la lecture automatique", fr.desctoggleautoplay());
    }

    @Test
    public void descInfoTest(){
        Assert.assertEquals("Affiche la musique en cours",fr.descinfo());
    }

    @Test
    public void nowPlayingTest(){
        Assert.assertEquals("♫ Now playing - Never Gonna Give You Up by Rick Astley ♫",fr.nowPlaying("Never Gonna Give You Up", "Rick Astley"));
    }

    @Test
    public void nothingPlayingTest(){
        Assert.assertEquals("Aucune musique n'est en train d'être jouée.",fr.nothingPlaying());
    }

    @Test
    public void succPlayTest(){
        Assert.assertEquals("La lecture a bien été (re)prise !",fr.succPlay());
    }

    @Test
    public void toggleAutoPlayOnTest(){
        Assert.assertEquals("La lecture automatique est désormais active !",fr.toggleAutoPlayOn());
    }

    @Test
    public void toggleAutoPlayOffTest(){
        Assert.assertEquals("La lecture automatique est désactivée",fr.toggleAutoPlayOff());
    }

    @Test
    public void didYouMeanTest(){
        Assert.assertEquals("Désolé, mais la commande halp n'existe pas. Vouliez-vous dire help ?",fr.didYouMean("halp","help"));
    }

    @Test
    public void errorPauseTest(){
        Assert.assertEquals("Rien n'est est en train d'être joué, réfléchis un peu ><",fr.errorPause());
    }

    @Test
    public void errorOnPlayTest(){
        Assert.assertEquals("La lecture n'a pas pu être reprise, n'hésite pas à réessayer si cela te semble étrange.", fr.errorOnPlay());
    }

    @Test
    public void notFoundTest(){
        Assert.assertEquals("Commande !nawak non trouvée",fr.notFound("!nawak"));
    }

    @Test
    public void undefinedBehaviorTest(){
        Assert.assertEquals("Alors, crois le ou non, mais il semblerait qu'on est oublié de définir le fonctionnement de cette commande 0:)",fr.undefinedBehavior());
    }

    @Test
    public void easterUndefinedBehaviorTest(){
        Assert.assertEquals("Ah, c'est un peu bête, je suis sûr qu'on avait pensé à un easter egg ici, mais on a du oublier :/",fr.easterUndefinedBehavior());
    }

    @Test
    public void wipTest(){
        Assert.assertEquals("Cette fonction est encore en cours de développement, elle peut ne pas être fonctionnelle", fr.wip());
    }

    @Test
    public void noPrevTest(){
        Assert.assertEquals("Désolé, mais aucune chanson n'a précédé celle-ci", fr.noPrev());
    }

    @Test
    public void easterShitTest(){
        Assert.assertEquals("merde",fr.easterShit());
    }

    @Test
    public void easterShitResponseTest(){
        Assert.assertEquals("Diantre*",fr.easterShitResponse());
    }

    @Test
    public void easterGoogleResponseTest(){
        Assert.assertEquals("Nan mais oh, tu me prends pour qui là ? ><",fr.easterGoogleResponse());
    }

    @Test
    public void easterNoResponseTest(){
        Assert.assertEquals("Si.",fr.easterNoResponse());
    }

    @Test
    public void easterPlopResponseTest(){
        Assert.assertEquals("Plop à toi, mon frère !", fr.easterPlopResponse());
    }

    @Test
    public void errorPlayTest(){
        Assert.assertEquals("Ça joue déjà, réfléchis un peu ><",fr.errorPlay());
    }

    @Test
    public void usagePaikeaTest(){
        Assert.assertEquals("Usage : !paikea",fr.usagepaikea());
    }

    @Test
    public void paikeaSongTest(){
        Assert.assertEquals("\"\\n\" +\n" +
                "            \"Uia mai koia, whakahuatia ake; \\n\" +\n" +
                "            \"Ko wai te whare nei e?\\n\" +\n" +
                "            \"Ko Te Kani / Ko Rangi / Whitireia! \\n\" +\n" +
                "            \"Ko wai te tekoteko kei runga? \\n\" +\n" +
                "            \"Ko Paikea! Ko Paikea! \\n\" +\n" +
                "            \"Whakakau Paikea. Hei! \\n\" +\n" +
                "            \"Whakakau he tipua. Hei! \\n\" +\n" +
                "            \"Whakakau he taniwha. Hei! \\n\" +\n" +
                "            \"Ka ū Paikea ki Ahuahu. Pakia! \\n\" +\n" +
                "            \"Kei te whitia koe \\n\" +\n" +
                "            \"ko Kahutia-te-rangi. Aue! \\n\" +\n" +
                "            \"Me ai tō ure ki te tamahine \\n\" +\n" +
                "            \"a Te Whironui - aue! -\\n\" +\n" +
                "            \"nāna i noho te Roto-o-tahe. \\n\" +\n" +
                "            \"Aue! Aue! \\n\" +\n" +
                "            \"He koruru koe, koro e.\"",fr.paikeSong());
    }
}
