package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.group.Group;
import pl.grzegorz2047.survivalcg.user.SurvUser;

import java.util.HashMap;

/**
 * Created by grzeg on 19.12.2015.
 */
public class GroupsManager {

    private final SurvivalCG plugin;

    public GroupsManager(SurvivalCG plugin){
        this.plugin = plugin;
    }

    private HashMap<String, Group> groups = new HashMap<String, Group>();


    public HashMap<String, Group> getGroups() {
        return groups;
    }


    public boolean createGroup(Player p, String tag){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
        if (!user.getGroup().equals("")) {
            p.sendMessage(plugin.getPrefix()+ ChatColor.GRAY + "Jestes juz w druzynie " + user.getGroup() + "!");
            return false;
        }
        if (tag.length() < 3 || tag.length() > 6) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Tag druzyny musi miec conajmniej 3 znaki i maxymalnie 6 znakow!");
            return false;
        }
        if (!tag.matches("[0-9a-zA-Z]*")) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nazwa druzyny posiada niewlasciwe znaki");
            return false;
        }
        boolean exists = plugin.getMysql().existsGroup(tag);
        if (exists) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Taka druzyna juz istnieje!");
            return false;
        }
        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) < 120) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Jestes za blisko spawnu!");
            return false;
        }
        Group group = new Group(tag, p.getName(), p.getLocation(), System.currentTimeMillis());
        plugin.getGroups().getGroups().put(tag, group);
        plugin.getMysql().insertGroup(group);
        plugin.getSc().getTeam(group.getDisplaytag()).setPrefix(group.getDisplaytag());
        plugin.getSc().getTeam(group.getDisplaytag()).setDisplayName(group.getDisplaytag());
        plugin.getSc().getTeam(group.getDisplaytag()).addEntry(p.getName());
        user.setGroup(group.getGroupname());
        plugin.getMysql().updatePlayer(user);
        return true;
    }

    public boolean deleteGroup(Player p){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

        if (user.getGroup().equalsIgnoreCase("")) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
            return false;
        }
        Group g = plugin.getGroups().getGroups().get(user.getGroup());
        if (!g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes liderem tej druzyny");
            return false;
        }
        for(String member: g.getMembers()){
            if(Bukkit.getPlayer(member) != null){
                SurvUser fuser = plugin.getPlayers().getUsers().get(member);
                fuser.setGroup("");
                plugin.getMysql().updatePlayer(fuser);
                plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(member);
            }else {
                SurvUser fuser = new SurvUser(member, false);
                plugin.getMysql().updateGuildPlayer(fuser);
            }
        }
        plugin.getSc().getTeam(g.getDisplaytag()).unregister();
        plugin.getMysql().deleteGroup(g);
        plugin.getGroups().getGroups().remove(user.getGroup());
        return true;
    }

    public boolean addToGroup(Player p, String groupname){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
        if (!user.getGroup().equalsIgnoreCase("")) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Musisz opusic obecna grupe zanim dolaczysz do innej!");
            return false;
        }
        Group g = plugin.getGroups().getGroups().get(groupname);
        if (g == null) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Taka gildia nie istnieje badz nie ma jej zadnych czlonkow!");
            return false;
        }
        if (!g.getWaiting().contains(p.getName())) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Twoje zaproszenie nie istnieje!");
            return false;
        }
        g.getMembers().add(p.getName());
        user.setGroup(g.getGroupname());
        plugin.getSc().getTeam(g.getDisplaytag()).addEntry(p.getName());
        plugin.getMysql().updatePlayer(user);
        g.getWaiting().remove(p.getName());
        return true;
    }

    public boolean removeFromGroup(Player p, String whoKick){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

        if (user.getGroup().equalsIgnoreCase("")) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
            return false;
        }
        Group g = plugin.getGroups().getGroups().get(user.getGroup());
        if (!g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes liderem tej druzyny");
            return false;
        }
        if (g.getLeader().equalsIgnoreCase(whoKick)) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie mozesz siebie wyrzucic z druzyny!");
            return false;
        }
        if (!g.getMembers().contains(whoKick)) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz nie jest w twojej druzynie!");
            return false;
        }
        g.getMembers().remove(whoKick);
        Player fp = Bukkit.getPlayer(whoKick);
        if (fp != null) {
            SurvUser fuser = plugin.getPlayers().getUsers().get(fp.getName());
            fuser.setGroup("");
            plugin.getMysql().updatePlayer(fuser);
            plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(whoKick);
        } else {
            SurvUser fuser = new SurvUser(whoKick, false);//Jezeli nie ma gracza online to sztucznie stworz usera
            plugin.getMysql().updateGuildPlayer(fuser);//Do poprawy, jakis enum i fajnie
        }

        return true;
    }

    public boolean leaveGroup(Player p){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());

        if (user.getGroup().equalsIgnoreCase("")) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w zadnej druzynie!");
            return false;
        }
        Group g = plugin.getGroups().getGroups().get(user.getGroup());
        if (g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Jestes liderem tej druzyny! Aby wyjsc z druzyny musisz ja usunac!");
            return false;
        }
        plugin.getSc().
                getTeam(g.
                        getDisplaytag()).
                removeEntry(user.getUsername());
        boolean isSomebody = false;
        for (String member : g.getMembers()) {
            if (Bukkit.getPlayer(member) != null) {
                isSomebody = true;
            }
        }
        if (!isSomebody) {
            plugin.getSc().getTeam(g.getDisplaytag()).unregister();
            plugin.getGroups().getGroups().remove(user.getGroup());
        }
        user.setGroup("");
        plugin.getMysql().updatePlayer(user);
        return true;
    }

    public boolean teleportHome(Player p){
        SurvUser user = plugin.getPlayers().getUsers().get(p.getName());
        if (!user.getGroup().equalsIgnoreCase("")) {
            final Location loc = p.getLocation();
            final String username = user.getUsername();
            Group g = plugin.getGroups().getGroups().get(user.getGroup());
            if (g != null) {
                // System.out.print("g nie jest null");
                p.teleport(g.getHome());
            }
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Zostales przeteleportowany!");
            return true;
        } else {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Nie jestes w druzynie!");
            return false;
        }
    }
}
