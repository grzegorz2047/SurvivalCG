package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by grzeg on 20.12.2015.
 */
public class RankingManager {
    private final SCG plugin; //Jakas baza danych by sie przydala :)

    public RankingManager(SCG plugin) {
        this.plugin = plugin;
    }

    private LinkedHashMap<String, Integer> userRank = new LinkedHashMap<String, Integer>();

    public LinkedHashMap<String, Integer> getGuildRank() {
        return guildRank;
    }

    public void setGuildRank(LinkedHashMap<String, Integer> guildRank) {
        this.guildRank = guildRank;
    }

    private LinkedHashMap<String, Integer> guildRank = new LinkedHashMap<String, Integer>();


    public void checkUserpoints() {
        plugin.getManager().getMysqlManager().getRankingQuery().getRankingUser(plugin.getManager().getRankingManager());
    }

    public void checkGuildpoints(String guildTag, Guild guild) {
        if (guildRank.get(guildTag) != null) {
            guildRank.put(guildTag, guild.getGuildPoints());
        } else {
            if (guildRank.size() < 15) {
                guildRank.put(guildTag, guild.getGuildPoints());
            } else {
                int maxVal = 0;
                String topGuildTag = "";
                for (Map.Entry<String, Integer> entry : guildRank.entrySet()) {
                    if (guild.getGuildPoints() > entry.getValue()) {
                        maxVal = entry.getValue();
                        topGuildTag = entry.getKey();
                    }
                }
                if (!topGuildTag.isEmpty()) {
                    guildRank.remove(topGuildTag);
                    guildRank.put(guildTag, guild.getGuildPoints());
                }
            }

        }
    }

    public void addUserPoints(String username, int points) {
        userRank.put(username, points);

    }

    public void addGuildPoints(String guildTag, int points) {
        guildRank.put(guildTag, points);
    }

    public void printUserRanking(Player p) {
        int index = 1;
        p.sendMessage(ColoringUtil.colorText("&7Topka graczy:"));
        for (Map.Entry<String, Integer> entry : userRank.entrySet()) {
            //System.out.println("Ustawiam wartosc "+entry.getValue()+" dla osoby "+entry.getKey());
            StringBuilder sb = new StringBuilder();
            p.sendMessage(ColoringUtil.colorText(index++ + ". " + entry.getKey() + ": " + entry.getValue()));
        }
    }

    public void reCountGuildPoints(Guild guild) {
        int guildPoints = 0;
        int guildSize = guild.getMembers().size();
        for (String member : guild.getMembers()) {
            User u = new User(member);
            plugin.getManager().getMysqlManager().getUserQuery().getPlayer(u);
            guildPoints += u.getPoints();
        }
        guildPoints = guildPoints / guildSize;
        guild.setGuildPoints(guildPoints);
        plugin.getManager().getMysqlManager().getGuildQuery().updateGuild(guild);
    }

    /*
    https://metinmediamath.wordpress.com/2013/11/27/how-to-calculate-the-elo-rating-including-example/



     */
    public void recountEloFight(User winner, User lost) {
        int wOldPoints = winner.getPoints();
        int wKFactor = getKFactor(wOldPoints);

        int lOldPoints = lost.getPoints();
        int lKFactor = getKFactor(lOldPoints);


        double w = Math.pow(10, wOldPoints / 400);//R(1)
        double l = Math.pow(10, lOldPoints / 400);//R(2)

        double ew = w / (w + l);//E(1)
        double el = l / (w + l);//E(2)

        int ws = 1;//S(1)
        int ls = 0;//S(2)

        double wNewPoints = wOldPoints + wKFactor * (ws - ew);
        double lNewPoints = lOldPoints + wKFactor * (ls - el);

        //Bukkit.broadcastMessage("Nowe punkty z " + wOldPoints + " na " + wNewPoints);
        //Bukkit.broadcastMessage("Nowe punkty z " + lOldPoints + " na " + lNewPoints);

        winner.setPoints((int) wNewPoints);
        lost.setPoints((int) lNewPoints);

        int wDiff = (int) (wOldPoints - wNewPoints);
        int lDiff = (int) (lOldPoints - lNewPoints);

        Bukkit.broadcastMessage(plugin.getManager().getMsgManager().
                getMsg("killerkilledvictim").
                replace("{VICTIM}", lost.getUsername()).
                replace("{KILLER}", winner.getUsername()).
                replace("{VICTIMLOSE}", -lDiff + "").
                replace("{KILLEREARN}", -wDiff + ""));
    }


    public int getKFactor(int wOldPoints) {
        int wKFactor = 30;
        if (wOldPoints < 2000) {
            wKFactor = 30;
        } else if (wOldPoints >= 2000 && wOldPoints <= 2400) {
            wKFactor = 130 - ((wOldPoints) / 20);
        } else if (wOldPoints > 2400) {
            wKFactor = 10;
        }
        return wKFactor;
    }


    public LinkedHashMap<String, Integer> getUserRank() {
        return userRank;
    }
}
