package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by grzeg on 26.12.2015.
 */
public class GuildManager {
    private final SCG plugin;


    private HashMap<String, Guild> guilds = new HashMap<String, Guild>();

    public GuildManager(SCG plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, Guild> getGuilds() {
        return guilds;
    }


    public boolean createGroup(Player p, String tag) {
        MsgManager msgManager = plugin.getManager().getMsgManager();
        SettingsManager settingsManager = plugin.getManager().getSettingsManager();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if(settingsManager.getBlockedWorlds().contains(p.getWorld().getName())){
            p.sendMessage(msgManager.getMsg("forbiddenworld"));
            return false;
        }

        if (user.getGuild() != null) {
            p.sendMessage(msgManager.getMsg("alreadyinguild"));
            return false;
        }
        if (tag.length() < 3 || tag.length() > 6) {
            p.sendMessage(msgManager.getMsg("toolongshorttag").replace("{MIN}",settingsManager.getMinClanTag()+"").replace("{MAX}", settingsManager.getMaxClanTag()+""));
            return false;
        }
        if (!tag.matches("[0-9a-zA-Z]*")) {
            p.sendMessage(msgManager.getMsg("illegaltag"));
            return false;
        }
        boolean exists = plugin.getManager().getGuildManager().getGuilds().get(tag) != null;
        if (exists) {
            p.sendMessage(msgManager.getMsg("guildexists"));
            return false;
        }
        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) < settingsManager.getProtectedSpawnRadius()) {
            p.sendMessage(msgManager.getMsg("spawn-too-close"));
            return false;
        }
        Guild guild = new Guild(tag, p.getName(), p.getLocation(), System.currentTimeMillis());
        plugin.getManager().getGuildManager().getGuilds().put(tag, guild);
        plugin.getManager().getMysqlManager().getGroupQuery().insertGroup(guild);
        plugin.getSc().getTeam(guild.getDisplaytag()).setPrefix(guild.getDisplaytag());
        plugin.getSc().getTeam(guild.getDisplaytag()).setDisplayName(guild.getDisplaytag());
        plugin.getSc().getTeam(guild.getDisplaytag()).addEntry(p.getName());
        user.setGuild(guild);
        plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(user);
        return true;
    }

    public boolean deleteGroup(Player p, boolean force) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (user.getGuild() == null && !force) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return false;
        }
        Guild g = user.getGuild();
        if (!g.getLeader().equalsIgnoreCase(p.getName()) && !force) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotleader"));
            return false;
        }
        for (String member : g.getMembers()) {
            if (Bukkit.getPlayer(member) != null) {
                User fuser = plugin.getManager().getUserManager().getUsers().get(member);
                fuser.setGuild(null);
                plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(fuser);
                plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(member);
            } else {
                User fuser = new User(member);
                plugin.getManager().getMysqlManager().getUserQuery().updateGuildPlayer(fuser);
            }
        }
        plugin.getSc().getTeam(g.getDisplaytag()).unregister();
        plugin.getManager().getMysqlManager().getGroupQuery().deleteGroup(g.getGuildName());
        plugin.getManager().getGuildManager().getGuilds().remove(user.getGuild().getGuildName());
        return true;
    }

    public boolean addToGroup(Player p, String guildname) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        if (user.getGuild() != null) {
            p.sendMessage(plugin.getPrefix() + ChatColor.GRAY + "Musisz opusic obecna grupe zanim dolaczysz do innej!");
            return false;
        }
        Guild g = plugin.getManager().getGuildManager().getGuilds().get(guildname);
        if (g == null) {
            p.sendMessage(plugin.getPrefix() + ChatColor.GRAY + "Taka grupa nie istnieje badz nie ma jej zadnych czlonkow!");
            return false;
        }
        if (!g.getWaiting().contains(p.getName())) {
            p.sendMessage(plugin.getPrefix() + ChatColor.GRAY + "Twoje zaproszenie nie istnieje!");
            return false;
        }
        g.getMembers().add(p.getName());
        user.setGuild(g);
        plugin.getSc().getTeam(g.getDisplaytag()).addEntry(p.getName());
        plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(user);
        g.getWaiting().remove(p.getName());
        Player leader = Bukkit.getPlayer(g.getLeader());
        String msg = plugin.getManager().getMsgManager().getMsg("broadcast-join").replace("{PLAYER}",p.getName()).replace("{TAG}",g.getGuildName());
        for(Player pl : Bukkit.getOnlinePlayers()){
            pl.sendMessage(msg);
        }
        return true;
    }

    public boolean removeFromGroup(Player p, String whoKick) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (user.getGuild() == null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return false;
        }
        Guild g = user.getGuild();
        if (!g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotleader"));
            return false;
        }
        if (g.getLeader().equalsIgnoreCase(p.getName()) ) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("kickleader"));
            return false;
        }
        if (!g.getMembers().contains(whoKick)) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotinthisguild"));
            return false;
        }
        g.getMembers().remove(whoKick);
        Player fp = Bukkit.getPlayer(whoKick);
        if (fp != null) {
            User fuser = plugin.getManager().getUserManager().getUsers().get(fp.getName());
            fuser.setGuild(null);
            plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(fuser);
            plugin.getSc().getTeam(g.getDisplaytag()).removeEntry(whoKick);
        } else {
            User fuser = new User(whoKick);//Jezeli nie ma gracza online to sztucznie stworz usera
            plugin.getManager().getMysqlManager().getUserQuery().updateGuildPlayer(fuser);//Do poprawy, jakis enum i fajnie
        }

        return true;
    }

    public boolean leaveGroup(Player p) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (user.getGuild() == null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return false;
        }
        Guild g = user.getGuild();
        if (g.getLeader().equalsIgnoreCase(p.getName()) ) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("kickleader"));
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
            plugin.getManager().getGuildManager().getGuilds().remove(user.getGuild().getGuildName());
        }
        user.setGuild(null);
        plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(user);
        return true;
    }

    public boolean requestTeleportHome(Player p) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        if (user.getGuild() != null) {
            final Location loc = p.getLocation();
            final String username = user.getUsername();
            Guild g = user.getGuild();
            if (g != null) {
                // System.out.print("g nie jest null");
                plugin.getManager().getTeleportManager().getRequests().
                        add(new TeleportRequest(user.getUsername(), loc, g.getHome(), System.currentTimeMillis(), plugin.getManager().getSettingsManager().getCooldownTpTime()));
            }
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("timetotp").replace("{TIME}",plugin.getManager().getSettingsManager().getCooldownTpTime()+""));
            return true;
        } else {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
                return false;
        }
    }


}
