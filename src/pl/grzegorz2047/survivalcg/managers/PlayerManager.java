package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.survivalcg.user.SurvUser;

import java.util.HashMap;

/**
 * Created by grzeg on 19.12.2015.
 */
public class PlayerManager {

    private HashMap<String, SurvUser> users = new HashMap<String, SurvUser>();

    public HashMap<String, SurvUser> getUsers() {
        return users;
    }
}
