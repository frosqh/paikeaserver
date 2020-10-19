package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.Model;

import java.util.Random;

public class User extends Model {
    public String username;
    public String mail;
    public String ytprofile;
    public String spprofile;
    public String deprofile;
    public String scprofile;
    public String ggprofile;

    protected User(int id) {
        super(id);
    }

    public User(int id, String username, String mail, String ytprofile, String spprofile, String deprofile, String scprofile, String ggprofile){
        super(id);
        this.username = username;
        this.ytprofile = ytprofile;
        this.spprofile = spprofile;
        this.deprofile = deprofile;
        this.scprofile = scprofile;
        this.ggprofile = ggprofile;
    }

    @Override
    public String toString(){
        return "User : "+username+" at "+mail;
    }

    public static String generatePassword(int codeLength){
        String alphabet="ABCDEFGHJKLMNPQRSTUVWXYZ1234567890";
        StringBuilder code = new StringBuilder();
        Random r = new Random();
        for (int i =0; i<codeLength ;i++){
            code.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return code.toString();
    }
}
