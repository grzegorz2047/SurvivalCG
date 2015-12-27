package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.HashMap;

/**
 * Created by grzeg on 26.12.2015.
 */
public class UserManager {


    private final SCG plugin;
    private HashMap<String, User> users = new HashMap<String, User>();

    public UserManager(SCG plugin){
        this.plugin = plugin;
    }


    public HashMap<String, User> getUsers() {
        return users;
    }

    public User addUser(String username){
        User user = new User(username);
        plugin.getManager().getMysqlManager().getUserQuery().getPlayer(user);
        plugin.getManager().getUserManager().getUsers().put(username, user);
        return user;
    }

    public void removeUser(String username) {
        plugin.getManager().getUserManager().getUsers().remove(username);
    }
}
