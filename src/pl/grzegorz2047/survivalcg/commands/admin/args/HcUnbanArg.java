package pl.grzegorz2047.survivalcg.commands.admin.args;

import org.bukkit.command.CommandSender;
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

    }

}
