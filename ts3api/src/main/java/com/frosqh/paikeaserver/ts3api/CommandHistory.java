package com.frosqh.paikeaserver.ts3api;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private class CommandEntry{
        private final String command;
        private final String user;
        private final String[] params;

        CommandEntry(String command, String user, String... params){
            this.command = command;
            this.user = user;
            this.params = params;
        }
    }

    private final List<CommandEntry> history;

    public CommandHistory(){
        history = new ArrayList<>();
    }

    public void append(CommandEntry e){
        history.add(e);
    }

    public void append(String command, String user, String... params){
        history.add(new CommandEntry(command, user, params));
    }
}
