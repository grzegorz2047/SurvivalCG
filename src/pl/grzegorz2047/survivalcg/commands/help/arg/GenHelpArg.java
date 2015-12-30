package pl.grzegorz2047.survivalcg.commands.help.arg;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class GenHelpArg extends Arg {

    SCG plugin;

    public GenHelpArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
