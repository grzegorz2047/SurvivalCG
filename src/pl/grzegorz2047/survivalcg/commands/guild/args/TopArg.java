package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class TopArg extends Arg {

    private final SCG plugin;

    public TopArg(Plugin plugin){
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        plugin.getManager().getRankingManager().printRanking(p);
    }
}
