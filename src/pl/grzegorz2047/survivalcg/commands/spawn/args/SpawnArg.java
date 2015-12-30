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

        if (sender.hasPermission("scg.tp.spawn")) {
            TeleportRequest request = new TeleportRequest
                    (p.getName(),
                            p.getLocation(),
                            p.getWorld().getSpawnLocation(),
                            System.currentTimeMillis(),
                            plugin.getManager().getSettingsManager().getCooldownTpTime());
            plugin.getManager().getTeleportManager().getRequests().add(request);

        }
    }
}
