package old.grzegorz2047.survivalcg.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

/**
 * Created by grzeg on 23.12.2015.
 */
public class AdminCommand implements CommandExecutor {

    private final SCG plugin;

    public AdminCommand(SCG plugin) {
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
                    Guild g = plugin.getManager().getGuildManager().getGuilds().get(groupname.toUpperCase());
                    if (g != null) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (g.getMembers().contains(p.getName())) {
                                boolean answer = plugin.getManager().getGuildManager().deleteGroup(p, true);
                                if (answer) {
                                    op.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddisbandsuccess").replace("{TAG}",g.getGuildName()));
                                    return true;
                                }
                            }
                        }
                    }else{
                        plugin.getManager().getMysqlManager().getGroupQuery().deleteGroup(groupname);
                    }
                }
            }
        }else{
            op.sendMessage(plugin.getManager().getMsgManager().getMsg("nopermission"));
        }
        return true;
    }
}
