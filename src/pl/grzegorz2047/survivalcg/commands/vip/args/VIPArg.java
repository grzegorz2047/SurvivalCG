package pl.grzegorz2047.survivalcg.commands.vip.args;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class VIPArg extends Arg {
    private final SCG plugin;

    public VIPArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }
}
