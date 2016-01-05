package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcsg.double0negative.tabapi.TabAPI;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.Map;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class TabManager {

    private final SCG plugin;

    public TabManager(SCG plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player p) { //add player to the game, set their priority here
        TabAPI.setPriority(plugin, p, 2);
    }

    public void updateTab(Player p) { //update the tab for a player
        TabAPI.setTabString(plugin, p, 0, 1, ColoringUtil.colorText(" &6&lCraftGames"));
        TabAPI.setTabString(plugin, p, 2, 1, ColoringUtil.colorText("  &7" + Bukkit.getOnlinePlayers().size() + "&a/&7" + Bukkit.getMaxPlayers()));
        TabAPI.setTabString(plugin, p, 0, 0, ColoringUtil.colorText("&6&lTOP RANKING:"));

        int index = 1;
        for (Map.Entry<String, Integer> entry : plugin.getManager().getRankingManager().getUserRank().entrySet()) {
            String val = ColoringUtil.colorText(("&7" + (index) + ". " + entry.getKey()));
            if (val.length() > 16) {
                val.substring(0, 15);
            }
            TabAPI.setTabString(plugin, p, index, 0, val);
            index++;
        }
        TabAPI.setTabString(plugin, p, 4, 1, ColoringUtil.colorText("&6&lTwoje staty:"));

        UserManager userManager = plugin.getManager().getUserManager();
        User user = userManager.getUsers().get(p.getName());
        if(user != null){
            TabAPI.setTabString(plugin, p, 5, 1, ColoringUtil.colorText("&2Kill:&7 "+user.getKills()));
            TabAPI.setTabString(plugin, p, 6, 1, ColoringUtil.colorText("&2Death:&7 "+user.getDeaths()));
            TabAPI.setTabString(plugin, p, 7, 1, ColoringUtil.colorText("&2Pkt.:&7 "+user.getPoints()));
            if(user.getDeaths() != 0){
                TabAPI.setTabString(plugin, p, 8, 1, ColoringUtil.colorText("&2K/D:&7 "+user.getKills()/user.getDeaths()));
            }else{
                TabAPI.setTabString(plugin, p, 8, 1, ColoringUtil.colorText("&2K/D:&7 "+0));

            }
        }
        TabAPI.setTabString(plugin, p, 0, 2, ColoringUtil.colorText("&6&lTOP GILDIE:"));
        index = 1;
        for (Map.Entry<String, Integer> entry : plugin.getManager().getRankingManager().getGuildRank().entrySet()) {
            String val = ColoringUtil.colorText(("&7" + (index) + ". " + entry.getKey()));
            if (val.length() > 16) {
                val.substring(0, 15);
            }
            TabAPI.setTabString(plugin, p, index, 2, val);
            index++;
        }




        TabAPI.updatePlayer(p);

    }

    public void removePlayer(Player p) { //player died or left, return priority
        TabAPI.setPriority(plugin, p, -2); //-2 means this plugin isn't using the tab anymore, dont show
        TabAPI.updatePlayer(p); //always good to update the player after priority change so that the plugin with priority gets displayed
    }


}
