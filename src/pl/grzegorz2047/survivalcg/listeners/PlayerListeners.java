package pl.grzegorz2047.survivalcg.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import pl.grzegorz2047.survivalcg.group.Group;
import pl.grzegorz2047.survivalcg.managers.Fight;
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
        plugin.getMysql().getUserQuery().getPlayer(user);
        plugin.getPlayers().getUsers().put(username, user);
        e.setJoinMessage("");

        if (!e.getPlayer().hasPlayedBefore()) {
            if (e.getPlayer().hasPermission("lobby.svip")) {
                e.getPlayer().getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
                e.getPlayer().getInventory().setItem(1, new ItemStack(Material.STONE_AXE));
                e.getPlayer().getInventory().setItem(2, new ItemStack(Material.STONE_PICKAXE));
                e.getPlayer().getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
            } else {
                e.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
                e.getPlayer().getInventory().setItem(1, new ItemStack(Material.WOOD_AXE));
                e.getPlayer().getInventory().setItem(2, new ItemStack(Material.WOOD_PICKAXE));
                e.getPlayer().getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 2));

            }
        }
        //Z pewnych powodów musiałem ręcznie dodawać uprawnienia graczom
        e.getPlayer().addAttachment(plugin, "randomtp.signs.use", true);
        e.getPlayer().addAttachment(plugin, "randomtp.tp", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpa", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpaccept", true);
        e.getPlayer().addAttachment(plugin, "essentials.tpdeny", true);
        e.getPlayer().addAttachment(plugin, "essentials.home", true);
        e.getPlayer().addAttachment(plugin, "essentials.sethome", true);
        e.getPlayer().addAttachment(plugin, "essentials.delhome", true);
        e.getPlayer().addAttachment(plugin, "essentials.spawn", true);
        plugin.getScoreboardManager().prepareJoinScoreboard(e.getPlayer());


        if (!user.getGroup().equals("")) {
            Group group;
            if (this.plugin.getGroups().getGroups().get(user.getGroup()) == null) {
                group = new Group();
                group.setTag(user.getGroup());
                plugin.getMysql().getGroupQuery().getGroup(group);
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
        Player p = e.getPlayer();
        plugin.getScoreboardManager().refreshTitle(p.getScoreboard(), Bukkit.getOnlinePlayers().size()-1);
        String username = p.getName();
        SurvUser u = plugin.getPlayers().getUsers().get(username);
        if (!u.getGroup().equals("")) {
            Group group = plugin.getGroups().getGroups().get(u.getGroup());
            plugin.getScoreboardManager().clearPlayerGuildTag(group, p);
            boolean isSomebody = false;
            for (String member : group.getMembers()) {
                if (Bukkit.getPlayer(member) != null) {
                    isSomebody = true;
                }
            }
            if (!isSomebody) {
                plugin.getScoreboardManager().unregisterGuildTag(group);
                plugin.getGroups().getGroups().remove(u.getGroup());
            }

        }


        Fight f = plugin.getAntiLogoutManager().getFightList().get(p.getName());
        if (f != null) {
            p.damage(30);
            p.sendMessage(plugin.getPrefix() + "Wylogowales sie podczas walki! Straciles wszystkie przedmioty!");
            if (!f.getAttacker().equals(p.getName())) {
                Player attacker = Bukkit.getPlayer(f.getAttacker());
                if (attacker != null) {
                    attacker.sendMessage(plugin.getPrefix() + "Gracz " + ChatColor.RED + p.getName() + " wylogowal sie podczas walki!");

                }
            }
        }
        plugin.getPlayers().getUsers().remove(username);

    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().hasPermission("lobby.svip")) {
            e.getPlayer().getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
            e.getPlayer().getInventory().setItem(1, new ItemStack(Material.STONE_AXE));
            e.getPlayer().getInventory().setItem(2, new ItemStack(Material.STONE_PICKAXE));
            e.getPlayer().getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 6));
        } else {
            e.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
            e.getPlayer().getInventory().setItem(1, new ItemStack(Material.WOOD_AXE));
            e.getPlayer().getInventory().setItem(2, new ItemStack(Material.WOOD_PICKAXE));
            e.getPlayer().getInventory().setItem(3, new ItemStack(Material.COOKED_BEEF, 2));

        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        String victimname = victim.getName();
        SurvUser victimuser = plugin.getPlayers().getUsers().get(victimname);
        int points = 20;
        victimuser.setDeaths(victimuser.getDeaths() + 1);
        boolean canGivePoints = false;
        if (victim.getKiller() == null) {
            plugin.getMysql().getUserQuery().updatePlayer(victimuser);
            return;
        }
        if (victimuser.getPoints() > 0) {
            victimuser.setPoints(victimuser.getPoints() - points);
            if (victimuser.getPoints() == 0) {
                victim.sendMessage(plugin.getPrefix() + ChatColor.RED + "Stales sie FreeKillem!" + ChatColor.GRAY + " Bez eq nic nie zdzialasz!");
                Bukkit.broadcastMessage(plugin.getPrefix() + ChatColor.RED + victim.getName() + ChatColor.GRAY + " stal sie FreeKillem! Nie ma juz punktow rankingowych!");
            } else {
                victim.sendMessage(plugin.getPrefix() + "Straciles 1 punkt rankingowy za smierc przez gracza!");

            }
            plugin.getRanking().checkPoints(victimname, victimuser);
            canGivePoints = true;
        } else {
            victim.sendMessage(plugin.getPrefix() + ChatColor.RED + "Wciaz jestes FreeKillem!" + ChatColor.GRAY + " Bez eq nic nie zdzialasz!");
        }
        plugin.getMysql().getUserQuery().updatePlayer(victimuser);
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            String killername = killer.getName();
            Score kills = plugin.getSc().getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(killername);
            SurvUser killuser = plugin.getPlayers().getUsers().get(killername);
            if (killuser != null) {
                killuser.setKills(killuser.getKills() + points);
                if (canGivePoints) {
                    killuser.setPoints(killuser.getPoints() + points);
                    plugin.getRanking().checkPoints(killername, killuser);
                    killuser.getPlayer().sendMessage(plugin.getPrefix() + ChatColor.GRAY + "Otrzymales " + ChatColor.GOLD + points + ChatColor.GRAY + " punkt rankingowy za zabicie " + ChatColor.RED + victimname + ChatColor.GRAY + "!");
                    Bukkit.broadcastMessage(plugin.getPrefix()+"["+ChatColor.GOLD+killuser.getGroup()+ChatColor.GRAY+"]"+" Gracz "+ChatColor.RED+killername+" "+""+" zabil gracza "+"["+ChatColor.GOLD+victimuser.getGroup()+ChatColor.GRAY+"]"+victimname+"!");
                }
                plugin.getMysql().getUserQuery().updatePlayer(killuser);

            }
        }
    }

    @EventHandler
    void onGameModeChange(PlayerGameModeChangeEvent e) {//Takie tam zabezpieczenie przed naduzywaniem rang moderatorskich
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.getPlayer().setFlying(!e.getPlayer().isFlying());
            e.getPlayer().sendMessage(plugin.getPrefix() + "Latanie jest ustawione na " + e.getPlayer().isFlying());
        }
    }
}
