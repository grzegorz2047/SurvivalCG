package pl.grzegorz2047.survivalcg.guild;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 19.12.2015.
 */
public class Guild {




    private String tag;
    private Location home;
    List<String> members = new ArrayList<String>() ;
    List<String> waiting = new ArrayList<String >();


    private String leader;
    private long createTime;

    public Guild(String tag, String leader, Location home, long createtime) {
        this.tag = tag;
        this.leader = leader;
        this.home = home;
        this.createTime = createtime;
        this.members.add(leader);
    }

    public Guild(String tag) {
        this.tag = tag;
    }

    public List<String> getWaiting() {
        return waiting;
    }

    public String getGuildName() {
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
        return ChatColor.GOLD+tag+ChatColor.GRAY+" ";
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean equals(Guild guild){
        return this.getGuildName().equals(guild.getGuildName());
    }

    public void setCreateTime(long createTime) {
        this.createTime  = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }
}
