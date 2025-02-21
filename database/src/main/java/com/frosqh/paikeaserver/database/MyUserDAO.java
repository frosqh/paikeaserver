package com.frosqh.paikeaserver.database;

import com.frosqh.daolibrary.DAO;
import com.frosqh.paikeaserver.database.exceptions.ObjectNotFound;

import java.util.List;
import java.util.Map;

public class MyUserDAO  {

    private DAO<User> userDAO;

    public MyUserDAO(){
        this.userDAO = DAO.construct(User.class);
    }

    private User getUserByGoogleIdAux(String googleID) throws ObjectNotFound {
        List<User> users = userDAO.filter("ggprofile", googleID);
        if (users.size() != 1) {
            throw new ObjectNotFound(User.class);
        } else {
            return users.get(0);
        }
    }

    public User getUserByGoogleId(String sub, String name, String mail) {
        User user;
        try {
            user = getUserByGoogleIdAux(sub);
        } catch (ObjectNotFound e) {
            DAO<User> userDAO = DAO.construct(User.class);
            user = new User(0, name,  mail, "", "", "", "", sub);
            user = userDAO.create(user);
        }
        return user;
    }


    public User getUserByGoogleId(Map userAttributes) {
        User user;
        String sub = (String) userAttributes.get("sub");
        String name = (String) userAttributes.get("name");
        String mail = (String) userAttributes.get("email");
        try {
            user = getUserByGoogleIdAux(sub);
        } catch (ObjectNotFound e) {
            DAO<User> userDAO = DAO.construct(User.class);
            user = new User(0, name,  mail, "", "", "", "", sub);
            user = userDAO.create(user);
        }
        return user;
    }
}
