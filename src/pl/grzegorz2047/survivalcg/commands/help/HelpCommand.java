package pl.grzegorz2047.survivalcg.commands.help;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.commands.help.arg.GenHelpArg;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class HelpCommand extends BaseWithAliasCommand{

    private SCG plugin;

    public HelpCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"", "?","pomoc","h","pomocy"},new GenHelpArg(plugin));
    }
}
