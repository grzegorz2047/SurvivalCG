package pl.grzegorz2047.survivalcg.commands.worldspawn.args;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class SetArg implements Arg {
    private final SCG plugin;

    public SetArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("survivalcg.setworldspawn")) {
            Player p = (Player) sender;
            Location loc = p.getLocation();
            p.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(),loc.getBlockZ());
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("setworldspawn"));
        }
    }
}
