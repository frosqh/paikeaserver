package com.frosqh.paikeaserver.player;

public enum PlayMode {
    NORMAL(false, "normal mode"),
    DM(true, "DM mode");

    private int uid;
    private String password;
    private final boolean allBlock;
    private final String name;
    private static Player player;

    PlayMode(boolean allBlock, String name){
        this.allBlock = allBlock;
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isPasswordValid(String password){
        return password.equals(this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllBlock() {
        return allBlock;
    }

    public String getName() {
        return name;
    }
}
