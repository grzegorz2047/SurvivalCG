package old.grzegorz2047.survivalcg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by grzeg on 23.12.2015.
 */
public class RandomTpCommand implements CommandExecutor {

    private final SurvivalCG plugin;

    public RandomTpCommand(SurvivalCG plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        plugin.getRandomTpManager().teleport(p,1500,500, false);
        return true;
    }
}
