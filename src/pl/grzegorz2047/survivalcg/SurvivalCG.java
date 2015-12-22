package pl.grzegorz2047.survivalcg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;
import pl.grzegorz2047.api.util.NameTagUtil;
import pl.grzegorz2047.survivalcg.commands.DruzynaCommands;
import pl.grzegorz2047.survivalcg.listeners.PlayerChatListeners;
import pl.grzegorz2047.survivalcg.listeners.PlayerListeners;
import pl.grzegorz2047.survivalcg.managers.GroupsManager;
import pl.grzegorz2047.survivalcg.managers.PlayerManager;
import pl.grzegorz2047.survivalcg.managers.RankingManager;
import pl.grzegorz2047.survivalcg.mysql.Mysql;
import pl.grzegorz2047.survivalcg.user.SurvUser;

/**
 * Created by grzeg on 19.12.2015.
 */
public class SurvivalCG extends JavaPlugin {

    private NameTagUtil util;
    private GroupsManager groups;
    private PlayerManager players;
    private RankingManager ranking;
    private Mysql mysql;
    private String prefix = ChatColor.GRAY + "[" + ChatColor.GOLD + "SCG" + ChatColor.GRAY + "] ";

    public GroupsManager getGroups() {
        return groups;
    }

    public PlayerManager getPlayers() {
        return players;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        String host = this.getConfig().getString("mysql.host");
        int port = this.getConfig().getInt("mysql.port");
        String db = this.getConfig().getString("mysql.db");
        String user = this.getConfig().getString("mysql.user");
        String table = this.getConfig().getString("mysql.table");
        String groupsTable = this.getConfig().getString("mysql.groupstable");
        String password = this.getConfig().getString("mysql.password");
        mysql = new Mysql(host, port, user, password, db, table, groupsTable);
        util = new NameTagUtil(true);
        groups = new GroupsManager();
        players = new PlayerManager();
        ranking = new RankingManager();
        mysql.getRanking(ranking);
        ranking.refreshScoreboard(util.getScoreboard());
        util.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.GOLD + "Ranking CG" + ChatColor.GRAY + ", Online: " + ChatColor.GREEN + "" + (Bukkit.getOnlinePlayers().size()));

        Bukkit.getPluginManager().registerEvents(new PlayerListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListeners(this), this);
        Bukkit.getPluginCommand("ranking").setExecutor(this);
        Bukkit.getPluginCommand("komendy").setExecutor(this);
        Bukkit.getPluginCommand("druzyna").setExecutor(new DruzynaCommands(this));
        Bukkit.getPluginCommand("drop").setExecutor(this);
    }

    @Override
    public void onDisable() {
        util = null;
        groups = null;
        players = null;
        ranking = null;
        mysql = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ranking")) {
            SurvUser killuser = this.getPlayers().getUsers().get(sender.getName());
            Player p = (Player) sender;
            p.sendMessage(ChatColor.GOLD + "=========================");
            p.sendMessage(ChatColor.GRAY + "Informacje o twoim koncie:");
            p.sendMessage(ChatColor.GRAY + "Liczba punktow: " + ChatColor.GREEN + killuser.getPoints());
            p.sendMessage(ChatColor.GRAY + "Liczba zabojstw: " + ChatColor.GREEN + killuser.getKills());
            p.sendMessage(ChatColor.GRAY + "Liczba smierci: " + ChatColor.GREEN + killuser.getDeaths());
            if (!killuser.getGroup().equalsIgnoreCase("")) {
                p.sendMessage(ChatColor.GRAY + "Aktualnie jest w druzynie " + ChatColor.GREEN + killuser.getGroup());
            } else {
                p.sendMessage(ChatColor.GRAY + "Nie jestes w zadnej druzynie!");

            }
            if (killuser.getDeaths() > 0) {
                p.sendMessage(ChatColor.GRAY + "Liczba zabojstw/liczba smierci (K/D): " + ChatColor.GREEN + ((float) killuser.getKills() / killuser.getDeaths()));

            } else {
                p.sendMessage(ChatColor.GRAY + "Liczba zabojstw/liczba smierci (K/D): " + ChatColor.GREEN + 0);

            }
            p.sendMessage(ChatColor.GOLD + "=========================");

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("komendy")) {
            SurvUser user = this.getPlayers().getUsers().get(sender.getName());
            Player p = (Player) sender;
            p.sendMessage(prefix + ChatColor.GRAY + "Dostepne komendy:");
            p.sendMessage(prefix + ChatColor.GRAY + "/tpa nick - Teleportacja do gracza za pozwoleniem");
            p.sendMessage(prefix + ChatColor.GRAY + "/sethome - Ustawienie sobie domu");
            p.sendMessage(prefix + ChatColor.GRAY + "/home - Teleportacja do ustawionego domu");
            p.sendMessage(prefix + ChatColor.GRAY + "/delhome - Usuniecie obecnego domu");
            p.sendMessage(prefix + ChatColor.GRAY + "/spawn - Teleportacja na spawn");
            p.sendMessage(prefix + ChatColor.GRAY + "/ranking - Informacje o graczu");
            p.sendMessage(prefix + ChatColor.GRAY + "/druzyna - Komendy zwiazane z druzyna");
            p.sendMessage(prefix + ChatColor.GRAY + "/drop - Informacje nt. dropu");
        }
        if (cmd.getName().equalsIgnoreCase("drop")) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.GRAY + "Dropi z rudy!");
        }

        return true;
    }


    public NameTagUtil getSc() {
        return util;
    }

    public RankingManager getRanking() {
        return ranking;
    }

    public Mysql getMysql() {
        return mysql;
    }

    public String getPrefix() {
        return prefix;
    }
}
