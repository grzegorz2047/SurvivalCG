package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

import java.util.Map;

/**
 * Created by grzegorz2047 on 31.12.2015.
 */
public class ScoreboardTagsManager {
    private final SCG plugin;

    public ScoreboardTagsManager(SCG plugin) {
        this.plugin = plugin;
    }


    public void generateTags(Guild g) {
        Scoreboard mainsb = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Map.Entry<String, Guild> entry : plugin.getManager().getGuildManager().getGuilds().entrySet()) {
            Scoreboard sb = g.getGuildScoreboard();
            Team t = sb.registerNewTeam(entry.getKey());

            if (g.getAlly().contains(entry.getKey())) {
                String allyColor = plugin.getManager().getSettingsManager().getAllyTagColor();
                String displayName = ColoringUtil.colorText(allyColor + t.getName() + "&7 ");
                t.setPrefix(displayName);
            } else {
                String color = plugin.getManager().getSettingsManager().getEnemyTagColor();
                String displayName = ColoringUtil.colorText(color + t.getName() + "&7 ");
                t.setPrefix(displayName);
            }
            for (String member : entry.getValue().getMembers()) {
                t.addEntry(member);
            }
        }
    }

    public void addOrUpdateTag(Guild newGuild) {
        for (Map.Entry<String, Guild> entry : plugin.getManager().getGuildManager().getGuilds().entrySet()) {
            if (entry.getValue().equals(newGuild)){
                continue;
            }
            Scoreboard sb = entry.getValue().getGuildScoreboard();
            Team t = sb.getTeam(newGuild.getGuildName());
            if( t == null){
                t = sb.registerNewTeam(newGuild.getGuildName());
            }
            if (entry.getValue().getAlly().contains(newGuild.getGuildName())) {
                String allyColor = plugin.getManager().getSettingsManager().getAllyTagColor();
                String displayName = ColoringUtil.colorText(allyColor + t.getName() + "&7 ");
                t.setPrefix(displayName);
            } else {
                String color = plugin.getManager().getSettingsManager().getEnemyTagColor();
                String displayName = ColoringUtil.colorText(color + t.getName() + "&7 ");
                t.setPrefix(displayName);

            }
            for (String member : newGuild.getMembers()) {
                if(!t.hasEntry(member)){
                    t.addEntry(member);

                }
            }

            Scoreboard sbnew = newGuild.getGuildScoreboard();
            Team tn = sbnew.registerNewTeam(entry.getKey());
            String color = plugin.getManager().getSettingsManager().getEnemyTagColor();
            String displayName = ColoringUtil.colorText(color + tn.getName() + "&7 ");
            tn.setPrefix(displayName);
            for (String member : entry.getValue().getMembers()) {
                if(!tn.hasEntry(member)){
                    tn.addEntry(member);
                }
            }

        }
    }

    public void removeTag(Guild g){
        for (Map.Entry<String, Guild> entry : plugin
                .getManager().
                        getGuildManager().
                        getGuilds().
                        entrySet()) {
            if (entry.getValue().equals(g)){
                continue;
            }
            Scoreboard sb = entry.getValue().getGuildScoreboard();
            Team t = sb.getTeam(g.getGuildName());
            t.unregister();
        }
    }

    public void setRelationTag(Guild guild, Guild requestingGuild){
        Scoreboard sb = guild.getGuildScoreboard();

        Team t = sb.getTeam(requestingGuild.getGuildName());
        if (guild.getAlly().contains(requestingGuild.getGuildName())) {
            String allyColor = plugin.getManager().getSettingsManager().getAllyTagColor();
            String displayName = ColoringUtil.colorText(allyColor + t.getName() + "&7 ");
            t.setPrefix(displayName);
        } else {
            String color = plugin.getManager().getSettingsManager().getEnemyTagColor();
            String displayName = ColoringUtil.colorText(color + t.getName() + "&7 ");
            t.setPrefix(displayName);
        }
        for (String member : requestingGuild.getMembers()) {
            t.addEntry(member);
        }

        sb = requestingGuild.getGuildScoreboard();
        t = sb.getTeam(guild.getGuildName());
        if (requestingGuild.getAlly().contains(guild.getGuildName())) {
            String allyColor = plugin.getManager().getSettingsManager().getAllyTagColor();
            String displayName = ColoringUtil.colorText(allyColor + t.getName() + "&7 ");
            t.setPrefix(displayName);
        } else {
            String color = plugin.getManager().getSettingsManager().getEnemyTagColor();
            String displayName = ColoringUtil.colorText(color + t.getName() + "&7 ");
            t.setPrefix(displayName);
        }
        for (String member : guild.getMembers()) {
            t.addEntry(member);
        }
    }

}
