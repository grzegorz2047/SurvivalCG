package pl.grzegorz2047.survivalcg.group;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 19.12.2015.
 */
public class Group {




    private String tag;
    private Location home;
    List<String> members = new ArrayList<String>() ;
    List<String> waiting = new ArrayList<String >();


    private String leader;
    private Long createDate;
    public Group(String tag, String leader, Location home, long createtime) {
        this.tag = tag;
        this.leader = leader;
        this.home = home;
        this.createDate = createtime;
        this.members.add(leader);
    }

    public Group() {

    }

    public List<String> getWaiting() {
        return waiting;
    }

    public String getGroupname() {
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

    public Long getCreateDate() {
        return createDate;
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
}
