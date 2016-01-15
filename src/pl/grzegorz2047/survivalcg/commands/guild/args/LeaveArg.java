package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class LeaveArg implements Arg {
    private final SCG plugin;

    public LeaveArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        boolean left = plugin.getManager().getGuildManager().leaveGuild(p);
        if(left) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("leaveguildsuccess"));
        }
    }
}
