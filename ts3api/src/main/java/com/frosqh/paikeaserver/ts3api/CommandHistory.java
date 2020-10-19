package com.frosqh.paikeaserver.ts3api;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private static class CommandEntry{

        CommandEntry(String command, String user, String... params){
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
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
