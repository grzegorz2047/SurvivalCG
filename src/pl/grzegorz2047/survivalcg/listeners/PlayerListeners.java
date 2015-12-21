package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import pl.grzegorz2047.survivalcg.group.Group;
import pl.grzegorz2047.survivalcg.user.SurvUser;
import pl.grzegorz2047.survivalcg.SurvivalCG;

/**
 * Created by grzeg on 19.12.2015.
 */
public class PlayerListeners implements Listener {


    private final SurvivalCG plugin;

    public PlayerListeners(SurvivalCG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String username = p.getName();
        SurvUser user = new SurvUser(username, false);
        plugin.getMysql().getPlayer(user);
        plugin.getPlayers().getUsers().put(username, user);
        e.setJoinMessage("");

        if (!e.getPlayer().hasPlayedBefore()) {
            e.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
            e.getPlayer().getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 5));
        }

        e.getPlayer().addAttachment(plugin, "randomtp.signs.use", true);
        e.getPlayer().addAttachment(plugin, "randomtp.tp", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpa", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpaccept", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpdeny", true);
        e.getPlayer().addAttachment(plugin, "essentials.home", true);
        e.getPlayer().addAttachment(plugin, "essentials.sethome", true);
        e.getPlayer().addAttachment(plugin, "essentials.delhome", true);
        e.getPlayer().addAttachment(plugin, "essentials.spawn", true);
        e.getPlayer().setScoreboard(plugin.getSc().getScoreboard());
        if (e.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
            plugin.getSc().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.GOLD + "Ranking CG" + ChatColor.GRAY + ", Online: " + ChatColor.GREEN + "" + (Bukkit.getOnlinePlayers().size()));
        }


        if (!user.getGroup().equals("")) {
            Group group;
            if (this.plugin.getGroups().getGroups().get(user.getGroup()) == null) {
                group = new Group();
                group.setTag(user.getGroup());
                plugin.getMysql().getGroup(group);
                plugin.getGroups().getGroups().put(group.getGroupname(), group);
                plugin.getSc().getTeam(group.getDisplaytag()).setPrefix(group.getDisplaytag());
                plugin.getSc().getTeam(group.getDisplaytag()).setDisplayName(group.getDisplaytag());
            } else {
                group = plugin.getGroups().getGroups().get(user.getGroup());
            }
            plugin.getSc().getTeam(group.getDisplaytag()).addEntry(username);
        }
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
        if (e.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
            plugin.getSc().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.GOLD + "Ranking CG" + ChatColor.GRAY + ", Online: " + ChatColor.GREEN + "" + (Bukkit.getOnlinePlayers().size() - 1));
        }
        Player p = e.getPlayer();
        String username = p.getName();
        SurvUser u = plugin.getPlayers().getUsers().get(username);
        if (!u.getGroup().equals("")) {
            Group group = plugin.getGroups().getGroups().get(u.getGroup());
            plugin.getSc().
                    getTeam(group.
                            getDisplaytag()).
                    removeEntry(username);
            boolean isSomebody = false;
            for (String member : group.getMembers()) {
                if (Bukkit.getPlayer(member) != null) {
                    isSomebody = true;
                }
            }
            if (!isSomebody) {
                plugin.getSc().getTeam(group.getDisplaytag()).unregister();
                plugin.getGroups().getGroups().remove(u.getGroup());
            }

        }
        plugin.getPlayers().getUsers().remove(username);

    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().hasPermission("lobby.svip")) {
            e.getPlayer().getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
            e.getPlayer().getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 6));
        } else {
            e.getPlayer().getInventory().setItem(1, new ItemStack(Material.COOKED_BEEF, 2));

        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        String victimname = victim.getName();
        SurvUser victimuser = plugin.getPlayers().getUsers().get(victimname);
        victimuser.setDeaths(victimuser.getDeaths() + 1);
        boolean canGivePoints = false;
        if (victim.getKiller() == null) {
            plugin.getMysql().updatePlayer(victimuser);
            return;
        }
        if (victimuser.getPoints() > 0) {
            victimuser.setPoints(victimuser.getPoints() - 1);
            if (victimuser.getPoints() == 0) {
                victim.sendMessage(plugin.getPrefix()+ChatColor.RED + "Stales sie FreeKillem!" + ChatColor.GRAY + " Bez eq nic nie zdzialasz!");
                Bukkit.broadcastMessage(plugin.getPrefix()+ChatColor.GOLD + victim.getName() + " stal sie FreeKillem! Nie ma juz punktow rankingowych!");
            }else{
                victim.sendMessage(plugin.getPrefix()+"Straciles 1 punkt rankingowy za smierc przez gracza!");

            }
            plugin.getRanking().checkPoints(victimname, victimuser);
            canGivePoints = true;
        } else {
            victim.sendMessage(plugin.getPrefix()+ChatColor.RED + "Wciaz jestes FreeKillem!" + ChatColor.GRAY + " Bez eq nic nie zdzialasz!");
        }
        plugin.getMysql().updatePlayer(victimuser);
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            String killername = killer.getName();
            Score kills = plugin.getSc().getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(killername);
            SurvUser killuser = plugin.getPlayers().getUsers().get(killername);
            if (killuser != null) {
                if (plugin.getSc().getTeam(plugin.getSc().getFreeTag()).hasEntry(killername)) {
                    plugin.getSc().getTeam(plugin.getSc().getFreeTag()).removeEntry(killername);
                }
                killuser.setKills(killuser.getKills() + 1);
                if (canGivePoints) {
                    killuser.setPoints(killuser.getPoints() + 1);
                    plugin.getRanking().checkPoints(killername, killuser);
                    killuser.getPlayer().sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Otrzymales " + ChatColor.GOLD + "1" + ChatColor.GRAY + " punkt rankingowy za zabicie " + ChatColor.RED + victimname + ChatColor.GRAY + "!");
                    //killuser.getPlayer().sendMessage(ChatColor.GRAY + "Punkty sa przyznawane na razie TESTOWO i resetuja sie po wyjsciu z serwera!");
                }
                plugin.getMysql().updatePlayer(killuser);

            }
        }
    }

    @EventHandler
    void onEntityDamageEntity(EntityDamageByEntityEvent e){

    }


}
