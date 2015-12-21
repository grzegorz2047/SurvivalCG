package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.survivalcg.group.Group;

import java.util.HashMap;

/**
 * Created by grzeg on 19.12.2015.
 */
public class GroupsManager {

    private HashMap<String, Group> groups = new HashMap<String, Group>();


    public HashMap<String, Group> getGroups() {
        return groups;
    }
}
