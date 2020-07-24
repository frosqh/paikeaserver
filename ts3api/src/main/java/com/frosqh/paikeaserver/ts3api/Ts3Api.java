package com.frosqh.paikeaserver.ts3api;

import com.frosqh.daolibrary.ConnectionSQLite;
import com.frosqh.paikeaserver.database.*;
import com.frosqh.paikeaserver.locale.Locale;
import com.frosqh.paikeaserver.player.PlayMode;
import com.frosqh.paikeaserver.player.Player;
import com.frosqh.paikeaserver.settings.Settings;
import com.frosqh.paikeaserver.ts3api.exception.NotACommandException;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import java.util.Arrays;
import java.util.List;

public class Ts3Api {
    private final TS3Api api;
    private final List<String> knownUsers;
    private final Locale locale;
    private final CommandManager commandManager;
    private final Player player;
    private int groupID = -1;
    private Thread changeNameThread;
    private Song templateSong;

    public Ts3Api(String host, String login, String password, String botName, List<String> users, Locale locale, Player player){
        knownUsers = users;
        this.locale = locale;
        this.player = player;
        player.setMethod(this::toCalle);
        commandManager = new CommandManager(this.locale, this.player, this);
        templateSong = new Song(-1,locale.hiddenSong(),"","","");

        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost(host);
        TS3Query ts3Query = new TS3Query(ts3Config);
        ts3Query.connect();

        api = ts3Query.getApi();
        api.login(login, password);
        api.selectVirtualServerById(1);
        try {
            api.setNickname(botName);
        } catch (Exception ignored){}

        goToRightChannel();

        final int clientId = api.whoAmI().getId();

        api.registerEvent(TS3EventType.TEXT_CHANNEL);
        api.registerEvent(TS3EventType.TEXT_PRIVATE);
        api.addTS3Listeners(new OnReceiveListener(clientId));

        List<ServerGroup> l = api.getServerGroups();
        for (ServerGroup sg : l){
            if (sg.getName().contains("♪"))
                groupID = sg.getId();
        }
        if (groupID == -1){
            groupID = api.addServerGroup("♪♪");
        }

        welcomeAll();
    }

    private void goToRightChannel(){
        for (String user : knownUsers){
            Client tsClient = api.getClientByNameExact(user, true);
            if (tsClient != null && api.whoAmI().getChannelId() != tsClient.getChannelId()){
                api.moveQuery(tsClient.getChannelId());
                return;
            }
        }
    }

    private void welcomeAll(){
        for (String user : knownUsers){
            Client tsClient = api.getClientByNameExact(user, true);
            if (tsClient != null && !tsClient.isOutputMuted() && !tsClient.isAway())
                api.sendPrivateMessage(tsClient.getId(), locale.welcomeMessage());
        }
    }

    private class OnReceiveListener extends TS3EventAdapter {
        final int selfID;

        OnReceiveListener(int self) {
            selfID = self;
        }

        @Override
        public void onTextMessage( TextMessageEvent e) {
            if (e.getTargetMode()== TextMessageTargetMode.CHANNEL && e.getInvokerId()!=selfID){
                int id = e.getInvokerId();
                String command = e.getMessage().toLowerCase();
                if ("!invoke".equals(command))
                    api.sendPrivateMessage(id,locale.welcomeMessage());
            }
            if (e.getTargetMode() == TextMessageTargetMode.CLIENT && e.getInvokerId()!=selfID){
                int id = e.getInvokerId();

                String command = e.getMessage().toLowerCase();
                String[] args = command.split(" ");
                String ans;

                PlayMode mode = player.getMode();


                if (mode.isAllBlock() && id!=mode.getUid() && !"!toggledm".equals(args[0])){
                    api.sendPrivateMessage(id, locale.notAuthorized(player.getMode().getName()));
                    api.sendPrivateMessage(mode.getUid(),
                            locale.triedToConnect(id, api.getClientInfo(id).getNickname(), e.getMessage()));
                    return;
                } // Blocked or wrong password

                if (commandManager.isEaster(command))
                    ans = commandManager.execEaster(command);
                else {
                    try {
                        if (commandManager.isBase(command) ||args[0].equals("!help")&&args.length<2)
                            ans=commandManager.execBase(command);
                        else if (commandManager.isComplex(args[0]))
                            ans = commandManager.execComplex(command, id);
                        else
                            ans = "‼";
                    } catch (NotACommandException ignored) {
                        ans = "‼";
                    }
                }

                if (!"‼".equals(ans)){
                    api.sendPrivateMessage(id, ans);
                } else {
                    String almost;
                    try {
                        if ((almost = commandManager.isAlmostACommand(args[0]))!=null)
                            api.sendPrivateMessage(id, locale.didYouMean(args[0],almost));
                        else throw new NotACommandException();

                    } catch (NotACommandException ex) {
                        api.sendPrivateMessage(id, locale.notFound(args[0]));
                    }
                }
            }
        }
    }

    private void changeName(Song song, int startIndex){

        if (Thread.currentThread().isInterrupted())
            return;
        boolean rerun = song.getTitle().length()-startIndex >= 24;
        api.renameServerGroup(groupID, "♪♪ "+song.getTitle().substring(startIndex, Math.min(song.getTitle().substring(startIndex).length(), 24)+startIndex) + " ♪♪");
        if (api.getClientByNameExact("♫♫♫", true) != null) {
            int botID = api.getClientByNameExact("♫♫♫", true).getId();
            api.editClient(botID, ClientProperty.CLIENT_DESCRIPTION, song.getTitle() + " by " + song.getArtist());
        }
        startIndex++;
        if (rerun)
            if (song.getTitle().length()-startIndex < 24)
                changeName(song,0);
            else
                changeName(song, startIndex);
    }

    private void toCalle(){
        if (changeNameThread != null)
            changeNameThread.interrupt();
        changeNameThread = new Thread(()->changeName(player.getMode().isAllBlock()?templateSong:player.getPlaying(),0));
        changeNameThread.start();
    }
}
