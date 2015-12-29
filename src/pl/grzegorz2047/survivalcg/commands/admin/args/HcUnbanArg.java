package pl.grzegorz2047.survivalcg.commands.admin.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class HcUnbanArg extends Arg {

    private final SCG plugin;

    public HcUnbanArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length >=2){
            String playertoub = args[1];
            if(sender.isOp() || sender.hasPermission("scg.hardocre.unban")){
                plugin.getManager().getDeathManager().unbanPlayer(playertoub);
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("playerunbanned"));
            }
        }

    }

}
