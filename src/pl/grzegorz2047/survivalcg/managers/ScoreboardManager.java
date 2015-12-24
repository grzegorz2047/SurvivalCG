package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.group.Group;

/**
 * Created by grzeg on 23.12.2015.
 */
public class ScoreboardManager {

    private final SurvivalCG plugin;

    public ScoreboardManager(SurvivalCG plugin){
        this.plugin = plugin;
    }

    public void prepareJoinScoreboard(Player p){
        p.setScoreboard(plugin.getSc().getScoreboard());
        refreshTitle(p.getScoreboard(), Bukkit.getOnlinePlayers().size());
    }

    public void clearPlayerGuildTag(Group g, Player p){
        plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(p.getName());
    }
    public void unregisterGuildTag(Group g){
        plugin.getSc().getTeam(g.getDisplaytag()).unregister();
    }

    public void refreshTitle(Scoreboard sc, int number){
        if (sc.getObjective(DisplaySlot.SIDEBAR) != null) {
            plugin.getSc().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.GOLD + "Ranking CG" + ChatColor.GRAY + ", Online: " + ChatColor.GREEN + "" + number);
        }
    }

}
