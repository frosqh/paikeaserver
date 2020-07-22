package com.frosqh.paikeaserver.player.exceptions;

import com.frosqh.paikeaserver.settings.Settings;

public class EmptyHistoryException extends PlayerException {

    public EmptyHistoryException(){
        super(Settings.getInstance().getLocale().emptyHistory());
    }
}
