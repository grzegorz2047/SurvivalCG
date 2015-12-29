package pl.grzegorz2047.survivalcg.managers;

import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzeg on 23.12.2015.
 */
public class DeathManager {

    private final SCG plugin;

    public DeathManager(SCG plugin){
        this.plugin = plugin;
    }


    public Long getPlayer(String username){
        String value = plugin.getManager().getMysqlManager().getDeathQuery().getPlayer(username);
        return Long.valueOf(value);
    }
    public void isBanned(String username){

    }

    public void banPlayer(String username, long seconds){
        plugin.getManager().getMysqlManager().getDeathQuery().banPlayer(username,seconds);
    }
    public void unbanPlayer(String username){
        plugin.getManager().getMysqlManager().getDeathQuery().unbanPlayer(username);

    }

}
