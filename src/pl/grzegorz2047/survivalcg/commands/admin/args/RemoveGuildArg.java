package pl.grzegorz2047.survivalcg.commands.admin.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class RemoveGuildArg implements Arg {

    private final SCG plugin;

    public RemoveGuildArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length >=2){
            String guildname = args[1];
            Guild g = plugin.getManager().getGuildManager().getGuilds().get(guildname);
            if(g != null){
                plugin.getManager().getGuildManager().deleteGuild(g, true);
            }else{
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddoesntexists"));
            }
        }
    }

}
