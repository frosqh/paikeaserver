package com.frosqh.paikeaserver.webserver.utils;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

    public static String hashMail(String mail) {
        String trimmedMail = mail.trim();
        String loweredMail = trimmedMail.toLowerCase();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String hex = (new HexBinaryAdapter()).marshal(md5.digest(mail.getBytes()));
            return hex.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            return "HASH";
        }
    }

    public static String getIconURL(String mail){
        return "https:/www.gravatar.com/avatar/"+hashMail(mail)+"?d=identicon&r=x&s=200";
    }
}
