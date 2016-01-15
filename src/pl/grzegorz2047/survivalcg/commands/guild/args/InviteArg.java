package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class InviteArg implements Arg {
    private final SCG plugin;

    public InviteArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        if(args.length != 2){
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("wrongcmdargument"));
            return;
        }
        User user = plugin.getManager().getUserManager().getUsers().get(p.getName());

        if (user.getGuild() == null) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
            return;
        }
        Guild g = user.getGuild();
        if (!g.getLeader().equalsIgnoreCase(p.getName())) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotleader"));
            return;
        }
        String friend = args[1];
        if (g.getWaitingMembers().contains(friend)) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("askforaccept"));
            return;
        }
        Player fp = Bukkit.getPlayer(friend);
        String prefix = plugin.getManager().getSettingsManager().getPrefix();

        if (fp != null) {
            User fuser = plugin.getManager().getUserManager().getUsers().get(fp.getName());
            if (fuser.getGuild() != null) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("playerhasguild"));
                return ;
            } else {
                g.getWaitingMembers().add(friend);
                fp.sendMessage(prefix + ChatColor.GRAY + "Gracz " + g.getLeader() + " wyslal ci zaproszenie do druzyny " + g.getGuildTag() + "!");
                fp.sendMessage(prefix + ChatColor.GRAY + "Aby zaakceptowac wpisz /g akceptuj " + g.getGuildTag());
                fp.sendMessage(prefix + ChatColor.GRAY + "Aby odmowic wpisz /dg odrzuc " + g.getGuildTag());
                p.sendMessage(prefix + "Zaproszenie zostalo wyslane do gracza o nicku " + friend);
                return ;
            }
        } else {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("playeroffline"));
            return ;
        }
    }
}
