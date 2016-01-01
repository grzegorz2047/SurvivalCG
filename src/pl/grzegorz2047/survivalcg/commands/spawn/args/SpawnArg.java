package pl.grzegorz2047.survivalcg.commands.spawn.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.teleport.TeleportRequest;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class SpawnArg extends Arg {
    private final SCG plugin;

    public SpawnArg(SCG plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        int time = 0;
        if (!sender.hasPermission("scg.tp.spawn.delay")) { //Bukkit version will support this permissions probably xd
            time = plugin.getManager().getSettingsManager().getCooldownTpTime();
        }
            TeleportRequest request = new TeleportRequest
                    (p.getName(),
                            p.getLocation(),
                            p.getWorld().getSpawnLocation(),
                            System.currentTimeMillis(),
                            time);
            plugin.getManager().getTeleportManager().getRequests().add(request);
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("waitfortp").
                    replace("{TIME}", time + ""));

        //}
    }
}
