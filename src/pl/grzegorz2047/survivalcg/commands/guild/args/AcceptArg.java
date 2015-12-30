package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class AcceptArg extends Arg {
    private final SCG plugin;

    public AcceptArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length == 2){
            String groupname = args[1].toUpperCase();
            boolean accepted = plugin.getManager().getGuildManager().addToGuild(p, groupname);
            if(accepted){
                Bukkit.broadcastMessage(plugin.getManager().getMsgManager().getMsg("broadcast-join").replace("{TAG}",groupname).replace("{PLAYER}", p.getName()));
            }
        }else{
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("wrongcmdargument"));
        }


    }
}
