package pl.grzegorz2047.survivalcg.guild;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 19.12.2015.
 */
public class Guild {

    private String tag;
    private Location home;
    private List<String> members = new ArrayList<String>();
    private List<String> waitingMembers = new ArrayList<String>();
    private List<String> ally = new ArrayList<String>();

    private Scoreboard guildScoreboard;

    private String leader;
    private long createTime;
    private String description;
    private String guildName;

    public Guild(String tag, String guildName, String leader, Location home, long createtime) {
        this.tag = tag;
        this.guildName = guildName;
        this.leader = leader;
        this.home = home;
        this.createTime = createtime;
        guildScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.members.add(leader);
        this.description = "";
        //Create sponge
        Block sponge = home.getWorld().getBlockAt(home.getBlockX(), 50, home.getBlockZ());
        sponge.setType(Material.SPONGE);
    }

    public Guild(String tag) {
        this.tag = tag;
        guildScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    }

    public List<String> getWaitingMembers() {
        return waitingMembers;
    }

    public String getGuildTag() {
        return tag;
    }

    public Location getHome() {
        return home;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public String getLeader() {
        return leader;
    }

    public String getDisplaytag() {
        return ChatColor.GOLD + tag + ChatColor.GRAY + " ";
    }

    public void setGuildTag(String tag) {
        this.tag = tag;
    }

    public boolean equals(Guild guild) {
        return this.getGuildTag().equals(guild.getGuildTag());
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Scoreboard getGuildScoreboard() {
        return guildScoreboard;
    }

    public void setGuildScoreboard(Scoreboard guildScoreboard) {
        this.guildScoreboard = guildScoreboard;
    }

    public List<String> getAlly() {
        return ally;
    }

    public void setAlly(List<String> ally) {
        this.ally = ally;
    }

    public CharSequence getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }
}
