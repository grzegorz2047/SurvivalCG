package pl.grzegorz2047.survivalcg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SurvivalCG;
import pl.grzegorz2047.survivalcg.group.Group;

/**
 * Created by grzeg on 23.12.2015.
 */
public class AdminCommand implements CommandExecutor {

    private final SurvivalCG plugin;

    public AdminCommand(SurvivalCG plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player op = (Player) sender;
        if (sender.hasPermission("survivalcg.admin")) {
            if (args[0].equalsIgnoreCase("bangroup")) {

            }
            if (args[0].equalsIgnoreCase("rollback")) {//Jezeli bedzie zapisywalo sie logi z zabic gracza to bedzie mozna cofac zabrane punkty

            }
            if (args[0].equalsIgnoreCase("removegroup")) {
                if (args.length == 2) {
                    String groupname = args[1];
                    Group g = plugin.getGroups().getGroups().get(groupname.toUpperCase());
                    if (g != null) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (g.getMembers().contains(p.getName())) {
                                boolean answer = plugin.getGroups().deleteGroup(p, true);
                                if (answer) {
                                    op.sendMessage(plugin.getPrefix() + "Pomyslnie usunales druzyne " + ChatColor.RED + g.getGroupname() + "!");
                                    return true;
                                }
                            }
                        }
                    }else{
                        plugin.getMysql().getGroupQuery().deleteGroup(groupname);
                    }
                }
            }
        }else{
            op.sendMessage(plugin.getPrefix()+"Nie masz dostepu do tej komendy!");
        }
        return true;
    }
}
