
package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class KickArg extends Arg {
    private final SCG plugin;

    public KickArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player p = (Player) sender;
        if(args.length>=2){
            String whoKick = args[1];
            boolean kicked = plugin.getManager().getGuildManager().removeFromGuild(p, whoKick);
            if(kicked){
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("playerkicksuccess"));
            }
        }else{
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("checkghelpmsg"));
        }

    }
}
