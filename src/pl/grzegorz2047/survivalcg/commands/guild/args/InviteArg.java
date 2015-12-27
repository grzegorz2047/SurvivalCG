package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class InviteArg extends Arg {
    private final SCG plugin;

    public InviteArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SurvUser inviter = plugin.getPlayers().getUsers().get(p.getName());
        if (inviter.getGroup().equalsIgnoreCase("")) {
            p.sendMessage(plugin.getPrefix()+ ChatColor.GRAY + "Nie jestes w zadnej gildii!");
            return true;
        }
        Group g = plugin.getGroups().getGroups().get(inviter.getGroup());
        if (!g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Tylko liderzy druzyn moga zapraszac graczy!");
            return true;
        }
        if (g.getMembers().size() == 8) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Posiadasz maksymalna liczbe osob w druzynie (8)!");
            return true;
        }
        String friend = args[1];
        if (g.getWaiting().contains(friend)) {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz zostal juz zaproszony!");
            return true;
        }
        Player fp = Bukkit.getPlayer(friend);
        if (fp != null) {
            SurvUser fuser = plugin.getPlayers().getUsers().get(fp.getName());
            if (!fuser.getGroup().equalsIgnoreCase("")) {
                p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten osobnik jest juz w jakiejs druzynie!");
                return true;
            } else {
                g.getWaiting().add(friend);
                fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Gracz " + g.getLeader() + " wyslal ci zaproszenie do druzyny " + g.getDisplaytag() + "!");
                fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Aby zaakceptowac wpisz /druzyna akceptuj " + g.getGroupname());
                fp.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Aby odmowic wpisz /druzyna odrzuc " + g.getGroupname());
                p.sendMessage(plugin.getPrefix()+"Zaproszenie zostalo wyslane do gracza o nicku " + friend);
                return true;
            }
        } else {
            p.sendMessage(plugin.getPrefix()+ChatColor.GRAY + "Ten gracz nie jest obecnie na serwerze!");
            return true;
        }
    }
}
