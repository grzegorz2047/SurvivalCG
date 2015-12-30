package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.api.util.MiscUtils;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;
import pl.grzegorz2047.survivalcg.world.Cuboid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grzeg on 26.12.2015.
 */
public class GuildManager {

    private final SCG plugin;

    private HashMap<String, Guild> guilds = new HashMap<String, Guild>();
    private List<Relation> pendingRelations = new ArrayList<Relation>();

    public GuildManager(SCG plugin) {
        this.plugin = plugin;
        this.setGuilds(loadGuilds());
    }

    public HashMap<String, Guild> getGuilds() {
        return guilds;
    }

    public List<Relation> getPendingRelations() {
        return pendingRelations;
    }

    public boolean createGuild(Player p, String tag) {
        MsgManager msgManager = plugin.getManager().getMsgManager();
        SettingsManager settingsManager = plugin.getManager().getSettingsManager();
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (settingsManager.getBlockedWorlds().contains(p.getWorld().getName())) {
            p.sendMessage(msgManager.getMsg("forbiddenworld"));
            return false;
        }

        if (user.getGuild() != null) {
            p.sendMessage(msgManager.getMsg("alreadyinguild"));
            return false;
        }
        if (tag.length() < settingsManager.getMinClanTag() || tag.length() > settingsManager.getMaxClanTag()) {
            p.sendMessage(msgManager.getMsg("toolongshorttag").replace("{MIN}", settingsManager.getMinClanTag() + "").replace("{MAX}", settingsManager.getMaxClanTag() + ""));
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
        System.out.println("dystans create gildia " + p.getLocation().distance(p.getWorld().getSpawnLocation()) + "  wymagane " + settingsManager.getProtectedSpawnRadius());

        if (p.getLocation().distance(p.getWorld().getSpawnLocation()) < settingsManager.getProtectedSpawnRadius()) {
            p.sendMessage(msgManager.getMsg("spawn-too-close"));
            return false;
        }
        for (Map.Entry<String, Cuboid> entry : plugin.getManager().getCuboidManager().getCuboids().entrySet()) {
            if (p.getLocation().distance(entry.getValue().getCenter()) <= (entry.getValue().getRadius() * 2) + 3) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("cuboidtoocloseothers").replace("{GUILD}", entry.getValue().getGuild().getGuildName()));

                return false;
            }
        }
        if (!p.hasPermission("scg.create.bypass")) {
            if (!MiscUtils.hasEnoughItemsForGuild(plugin.getManager().getSettingsManager().getReqItems(), p.getInventory())) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("notenoughitems"));
                return false;
            }
            MiscUtils.removeRequiredItemsForGuild(plugin.getManager().getSettingsManager().getReqItems(), p.getInventory());

        }


        Guild guild = new Guild(tag, p.getName(), p.getLocation(), System.currentTimeMillis());
        Cuboid cuboid = new Cuboid(guild, plugin.getManager().getSettingsManager().getCuboidRadius());
        plugin.getManager().getCuboidManager().getCuboids().put(guild.getGuildName(), cuboid);
        plugin.getManager().getGuildManager().getGuilds().put(tag, guild);
        plugin.getManager().getMysqlManager().getGuildQuery().insertGuild(guild);
        // #TODO COS Z SCOREBOARDEM
        user.setGuild(guild);
        plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(user);
        return true;
    }

    public boolean deleteGuild(Player p, boolean force) {
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

                // #TODO COS Z SCOREBOARDEM
            } else {
                User fuser = new User(member);
                plugin.getManager().getMysqlManager().getUserQuery().updateGuildPlayer(fuser);
            }
        }
        // #TODO COS Z SCOREBOARDEM
        for(String ally : g.getAlly()){
            Guild allied = plugin.getManager().getGuildManager().getGuilds().get(ally);
            if(allied != null){
                removeRelation(g, allied);
                allied.getAlly().remove(g.getGuildName());
            }

        }
        plugin.getManager().getCuboidManager().getCuboids().remove(g.getGuildName());
        plugin.getManager().getMysqlManager().getGuildQuery().deleteGroup(g.getGuildName());
        plugin.getManager().getGuildManager().getGuilds().remove(g.getGuildName());

        return true;
    }

    public boolean addToGuild(Player p, String guildname) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
        if (user.getGuild() != null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("first-leave-guild"));
            return false;
        }
        Guild g = plugin.getManager().getGuildManager().getGuilds().get(guildname);
        if (g == null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddoesntexists"));
            return false;
        }
        if (!g.getWaitingMembers().contains(p.getName())) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotoninvitedlist"));
            return false;
        }
        g.getMembers().add(p.getName());
        user.setGuild(g);
        // #TODO COS Z SCOREBOARDEM
        plugin.getManager().getMysqlManager().getUserQuery().updatePlayer(user);
        g.getWaitingMembers().remove(p.getName());
        String msg = plugin.getManager().getMsgManager().getMsg("broadcast-join").replace("{PLAYER}", p.getName()).replace("{TAG}", g.getGuildName());
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(msg);
        }
        return true;
    }

    public boolean removeFromGuild(Player p, String whoKick) {
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
        if (g.getLeader().equalsIgnoreCase(p.getName())) {
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
            // #TODO COS Z SCOREBOARDEM
        } else {
            User fuser = new User(whoKick);//Jezeli nie ma gracza online to sztucznie stworz usera
            plugin.getManager().getMysqlManager().getUserQuery().updateGuildPlayer(fuser);//Do poprawy, jakis enum i fajnie
        }

        return true;
    }

    public boolean leaveGuild(Player p) {
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (user.getGuild() == null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return false;
        }
        Guild g = user.getGuild();
        if (g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("kickleader"));
            return false;
        }
        // #TODO COS Z SCOREBOARDEM

        boolean isSomebody = false;
        for (String member : g.getMembers()) {
            if (Bukkit.getPlayer(member) != null) {
                isSomebody = true;
            }
        }
        if (!isSomebody) {
            // #TODO COS Z SCOREBOARDEM
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
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("hometpdontmove").replace("{TIME}", plugin.getManager().getSettingsManager().getCooldownTpTime() + ""));
            return true;
        } else {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return false;
        }
    }

    public HashMap<String, Guild> loadGuilds() {
        return plugin.getManager().getMysqlManager().getGuildQuery().loadGuilds();
    }

    public void setGuilds(HashMap<String, Guild> guilds) {
        this.guilds = guilds;
    }

    public void requestAlly(Guild requester, Guild withWho) {

        for (Relation r : plugin.getManager().getGuildManager().getPendingRelations()) {
            if(r.getWho().equals(requester.getGuildName()) && r.getWithWho().equals(withWho.getGuildName())){
                Player req = Bukkit.getPlayer(requester.getLeader());
                req.sendMessage(plugin.getManager().getMsgManager().getMsg("allyrequestpendingalready"));
                return;
            }
        }
        Relation r = new Relation(requester.getGuildName(), withWho.getGuildName(), Relation.Status.ALLY);
        this.pendingRelations.add(r);
        Player p = Bukkit.getPlayer(withWho.getLeader());
        p.sendMessage(plugin.getManager().getMsgManager().getMsg("sentallyrequestfrom").replace("{GUILD}", requester.getGuildName()));
    }

    public void removeRelation(Guild requester, Guild withWho) {
        Player p = Bukkit.getPlayer(withWho.getLeader());
        plugin.getManager().getMysqlManager().getRelationQuery().removeRelation(requester.getGuildName(),withWho.getGuildName());
    }

    public void checkPendingRelationList() {
        for (int i = 0; i < this.pendingRelations.size(); i++) {
            Relation r = this.pendingRelations.get(i);
            if(r.isExpired()){
                this.pendingRelations.remove(r);
            }
            if(System.currentTimeMillis() > r.getExpireDate()){
                this.pendingRelations.remove(r);
            }
        }
    }

}
