package pl.grzegorz2047.api.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Set;

/**
 * Created by Grzegorz2047. 29.09.2015.
 */
public class NameTagUtil {

    private static Scoreboard sc;
    Objective objective;
    private String defaultObjectiveName = "teams";
    private String freetag = ChatColor.GRAY+"["+ChatColor.GREEN+"IZI"+ChatColor.GRAY+"] "+ChatColor.WHITE+"";

    public NameTagUtil(boolean newScoreboard) {
        if (newScoreboard) {
            sc = Bukkit.getScoreboardManager().getNewScoreboard();
            objective = sc.registerNewObjective("dummy", defaultObjectiveName);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
        Validate.notNull(sc);
    }

    private void addTeam(String team) {
        Team t = sc.registerNewTeam(team);
        t.setDisplayName(team);
        t.setPrefix(ColoringUtil.colorText(normalizePrefix(team)));
    }

    public Team getTeam(String team) {
        if (sc.getTeam(team) == null) {
            addTeam(team);
        }
        return sc.getTeam(team);
    }

    public Scoreboard getScoreboard() {
        return sc;
    }

    public void colorTags(Player p) {

        for (Team t : getTeams()) {
            Team pt = p.getScoreboard().registerNewTeam(t.getName());
            pt.setDisplayName(t.getDisplayName());
            pt.setPrefix(t.getPrefix());
            for (String entry : t.getEntries()) {
                pt.addEntry(entry);
            }
        }
        //p.setScoreboard(sc);
    }

    public Set<Team> getTeams() {
        return sc.getTeams();
    }

    public String normalizePrefix(String team) {
        if (team.contains("1")) {
            return "&7[&a1&7] &7";
        }
        if (team.contains("2")) {
            return "&7[&b2&7] &7";
        }
        if (team.contains("3")) {
            return "&7[&c3&7] &7";
        }
        if (team.contains("4")) {
            return "&7[&e4&7] &7";
        }
        return team;
    }

    public String getDefaultObjectiveName() {
        return defaultObjectiveName;
    }

    public String getFreeTag() {
        return freetag;
    }

}
