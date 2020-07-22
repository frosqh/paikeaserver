package com.frosqh.paikeaserver.player.exceptions;

import com.frosqh.paikeaserver.settings.Settings;

public class PauseException extends PlayerException {
    public PauseException(){
        super(Settings.getInstance().getLocale().pauseException());
    }
}
