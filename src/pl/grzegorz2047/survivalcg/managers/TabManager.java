package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mcsg.double0negative.tabapi.TabAPI;
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
        TabAPI.setTabString(plugin, p, 0, 1, ColoringUtil.colorText(" &9&lCraftGames"));
        TabAPI.setTabString(plugin, p, 2, 1, ColoringUtil.colorText("  &7" + Bukkit.getOnlinePlayers().size() + "&a/&7" + Bukkit.getMaxPlayers()));
        TabAPI.setTabString(plugin, p, 4, 0, ColoringUtil.colorText("&9&lTOP RANKING:"));
        int index = 6;
        for (Map.Entry<String, Integer> entry : plugin.getManager().getRankingManager().getRank().entrySet()) {
            String val = ColoringUtil.colorText(("&7" + (index - 5) + ". " + entry.getKey()));
            if (val.length() > 16) {
                val.substring(0, 15);
            }
            TabAPI.setTabString(plugin, p, index, 0, val);
            index++;
        }
        TabAPI.setTabString(plugin, p, 4, 2, ColoringUtil.colorText("&9&lTOP GILDIE:"));
        TabAPI.updatePlayer(p);

    }

    public void removePlayer(Player p) { //player died or left, return priority
        TabAPI.setPriority(plugin, p, -2); //-2 means this plugin isn't using the tab anymore, dont show
        TabAPI.updatePlayer(p); //always good to update the player after priority change so that the plugin with priority gets displayed
    }


}
