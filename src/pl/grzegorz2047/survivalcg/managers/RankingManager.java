package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

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


    public void checkUserpoints(String username, User user) {
        if (userRank.get(username) != null) {
            userRank.put(username, user.getPoints());
        } else {
            if (userRank.size() < 15) {
                userRank.put(username, user.getPoints());
            } else {
                int minVal = 0;
                String minusername = "";
                for (Map.Entry<String, Integer> entry : userRank.entrySet()) {
                    if (minusername.isEmpty()) {
                        minusername = entry.getKey();
                        minVal = entry.getValue();
                    } else {
                        if (minVal > entry.getValue()) {
                            minusername = entry.getKey();
                        }
                    }
                }
                if (!minusername.isEmpty()) {
                    if (user.getPoints() > minVal) {
                        userRank.remove(minusername);
                        userRank.put(username, user.getPoints());
                    }
                }
            }
        }
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

    public void recountEloFight(User winner, User lost){
        int wOldPoints = winner.getPoints();
        float wkd = (winner.getDeaths()/winner.getKills());
        if(wkd != 0){
           wkd = (winner.getDeaths()/winner.getKills());
        }else {
            wkd = 1;
        }
        float wNewPoints = wOldPoints + (winner.getConstant() * wkd );

        int lOldPoints = lost.getPoints();
        float lkd = (lost.getDeaths()/lost.getKills());
        if(lkd != 0){
            lkd = (lost.getKills()/lost.getDeaths());
        }else {
            lkd = 1;
        }
        float lNewPoints = lOldPoints - (lost.getConstant() * lkd );

        winner.setPoints((int) wNewPoints);
        lost.setPoints((int) lNewPoints);
        Bukkit.broadcastMessage(plugin.getManager().getMsgManager().
                getMsg("killerkilledvictim").
                replace("{VICTIM}", winner.getUsername()).
                replace("{KILLER}", lost.getUsername()).
                replace("{VICTIMLOSE}", "-" + (lost.getConstant() * lkd )).
                replace("{KILLEREARN}", "+" + (winner.getConstant() * wkd)));
    }

    public LinkedHashMap<String, Integer> getUserRank() {
        return userRank;
    }
}
