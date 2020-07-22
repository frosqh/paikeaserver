package com.frosqh.paikeaserver.player.exceptions;

import com.frosqh.paikeaserver.settings.Settings;

public class PlayException extends PlayerException {
    public PlayException(){
        super(Settings.getInstance().getLocale().playException());
    }
}
