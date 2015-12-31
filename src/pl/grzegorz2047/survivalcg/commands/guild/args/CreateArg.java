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
public class CreateArg extends Arg {
    private final SCG plugin;

    public CreateArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length >= 2){
            boolean created = plugin.getManager().getGuildManager().createGuild(p,args[1].toUpperCase());
            if(created){
                Bukkit.broadcastMessage(plugin.getManager().getMsgManager().getMsg("broadcast-create").replace("{TAG}", args[1].toUpperCase()).replace("{PLAYER}", p.getName()));
            }
        }else{
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("wrongcmdargument"));
        }

    }
}
