package pl.grzegorz2047.survivalcg.commands.admin.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 04.01.2016.
 */
public class ReloadArg extends Arg {

    private final SCG plugin;

    public ReloadArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length >=1){
            if(sender.isOp() || sender.hasPermission("scg.hardocre.reload")){
                plugin.getManager().getSettingsManager().loadSettings();
                plugin.getManager().getMysqlManager().getRankingQuery().getRankingGuilds(plugin.getManager().getRankingManager());
                plugin.getManager().getMysqlManager().getRankingQuery().getRankingUser(plugin.getManager().getRankingManager());
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("pluginreloaded"));
            }else {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("nopermission"));
            }
        }

    }


}
