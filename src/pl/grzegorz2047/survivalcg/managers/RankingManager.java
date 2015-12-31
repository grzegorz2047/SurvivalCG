package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 20.12.2015.
 */
public class RankingManager { //Jakas baza danych by sie przydala :)

    private HashMap<String, Integer> rank = new HashMap<String, Integer>();


    public void checkPoints(String username, User user) {
        if (rank.get(username) != null) {
            rank.put(username, user.getPoints());
        } else {
            if (rank.size() < 10) {
                rank.put(username, user.getPoints());
            } else {
                int maxVal = 0;
                String maxusername = "";
                for (Map.Entry<String, Integer> entry : rank.entrySet()) {
                    if (user.getPoints() > entry.getValue()) {
                        maxVal = entry.getValue();
                        maxusername = entry.getKey();
                    }
                }
                if (!maxusername.isEmpty()) {
                    rank.remove(maxusername);
                    rank.put(username, user.getPoints());
                }
            }
        }
    }
    public void addPoints(String username, int points) {
            rank.put(username, points);
    }

    public void printRanking(Player p) {
        int index = 1;
        p.sendMessage(ColoringUtil.colorText("&7Topka graczy:"));
        for (Map.Entry<String, Integer> entry : rank.entrySet()) {
            //System.out.println("Ustawiam wartosc "+entry.getValue()+" dla osoby "+entry.getKey());
            StringBuilder sb = new StringBuilder();
            p.sendMessage(ColoringUtil.colorText(index++ +". "+ entry.getKey()+": "+entry.getValue()));
        }
    }


    public HashMap<String, Integer> getRank() {
        return rank;
    }
}
