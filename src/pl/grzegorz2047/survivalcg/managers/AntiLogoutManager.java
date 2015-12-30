package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grzeg on 23.12.2015.
 */
public class AntiLogoutManager {
    private final SCG plugin;
    HashMap<String, Fight> fightList = new HashMap<String, Fight>();

    public AntiLogoutManager(SCG plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, Fight> getFightList() {
        return fightList;
    }
    public void checkFights(){
        List<String> toDelete = new ArrayList<String>();
        for (Map.Entry<String, Fight> entry : fightList.entrySet()) {
            if (entry.getValue().getEndCooldown() <= System.currentTimeMillis() ) {
                toDelete.add(entry.getKey());
            }
        }
        for(String user : toDelete){
            Player p = Bukkit.getPlayer(user);
            fightList.remove(user);
            if(p != null){
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("safe-logout"));
            }
        }
    }
}
