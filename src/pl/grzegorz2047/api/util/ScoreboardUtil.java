package pl.grzegorz2047.api.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.grzegorz2047.api.manager.MsgManager;

import java.util.HashMap;
import java.util.Map;


public class ScoreboardUtil {


    private String scKills = ChatColor.RED + "Zabojstwa";
    private String scMoney = ChatColor.GOLD + "Monety";
    private String scAlive = ChatColor.GREEN + "Pozostalo";
    private String scSpect = ChatColor.YELLOW + "Obserwatorzy";
    private String scWins = ChatColor.GOLD + "Wygrane";

    private String minigamePrefix = MsgManager.getPrefix();

    public String getScKills() {
        return scKills;
    }

    public String getScMoney() {
        return scMoney;
    }

    public String getScAlive() {
        return scAlive;
    }


    /************************************/

    private final Scoreboard scoreboard;
    private Objective objective;
    private final ChatColor scoreboardKey = ChatColor.GREEN;
    private ChatColor scoreboardValue = ChatColor.GRAY;
    private final HashMap<String, Integer> position = new HashMap<>();


    private static int i = 0; //use in animatedText

    public ScoreboardUtil(Player p, boolean newScoreboard) {
        if (newScoreboard) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            p.setScoreboard(scoreboard);
        } else {
            scoreboard = p.getScoreboard();
        }
        checkObjective();

    }

    private void checkObjective() {
        if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
            objective = scoreboard.registerNewObjective("MainScorebaord", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } else {
            objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        }

    }


    /**
     * @param key   - key, np. player name
     * @param a     - value of key, np. count kill for player
     * @param empty - Boolean value, use this if line must be empty
     */
    public void setScoreboard(String key, Object a, Boolean empty, Integer pos) {
        String value = String.valueOf(a);
        final String fkey1 = scoreboardKey + key;
        Integer findex1 = getPos(key, true);

        if (pos != null) {
            findex1 = pos;
            position.put(key + "_k", -(1 + position.size()));
        }

        Team team = scoreboard.getTeam(key);
        if (team == null) {
            team = scoreboard.registerNewTeam(key);
        } else {
            if (!empty) {
                team.setSuffix(ChatColor.WHITE + ": " + ChatColor.GRAY + value);
            } else {
                team.setSuffix(ChatColor.WHITE + " " + ChatColor.GRAY + value);
            }
            if (!team.getEntries().contains(key)) {
                team.addEntry(key);
            }
        }

        if (!scoreboard.getEntries().contains(key)) {
            setScore(objective, key, findex1);
        } else {
            //removeScoreboard(scoreboard, objective, key, false);
            setScore(objective, key, findex1);
        }
    }

    public void clearAll() {
        position.clear();
        for (String s : scoreboard.getEntries()) {
            scoreboard.resetScores(s);
        }
    }

    public void setScore(Objective o, String score, int value) {
        if (o == null) return;
        Score s = o.getScore(score);
        if (!o.getScore(score).isScoreSet())
            s.setScore(-1);
        s.setScore(value);
    }

    public void removeScoreboard(Scoreboard sb, Objective o, String key_str, Boolean key) {
        final int index = getPos(key_str, key);
        for (String s : sb.getEntries()) {
            Score score = o.getScore(s);
            if (score.getScore() == index) {
                sb.resetScores(s);
                return;
            } else if (score.getEntry().contains(key_str)) {
                sb.resetScores(s);
                return;
            }
        }
    }

    private String getSBValue(String value, String key) {
        return (ChatColor.COLOR_CHAR + "" + (char) getPos(key, false)) + scoreboardValue + " " + value;
    }

    private int getPos(String n, Boolean key) {
        n = n + (key ? "_k" : "_v");
        if (position.containsKey(n)) {
            return position.get(n);
        }
        int k = -(1 + position.size());
        position.put(n, k);

        return k;
    }

    public void setDisplayName(String name) {
        objective.setDisplayName(name);
    }

    public void setFormattedDisplayName(String name) {
        objective.setDisplayName(minigamePrefix + name);
    }

    public String setAnimatedText(Map<Integer, String> animatedText) {
        String text = animatedText.get(i);
        i++;
        if (text == null) {
            text = animatedText.get(0);
            i = 0;
        }

        objective.setDisplayName(text);
        return text;
    }

    public Objective getObjective() {
        return objective;
    }

    public String getMinigamePrefix() {
        return minigamePrefix;
    }

    public String getScWins() {
        return scWins;
    }

    public String getScSpect() {
        return scSpect;
    }
}