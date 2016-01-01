package pl.grzegorz2047.survivalcg.commands.cobblex;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 01.01.2016.
 */
public class CobbleXCommand extends BaseWithAliasCommand {

    SCG plugin;

    public CobbleXCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.plugin = (SCG) plugin;
        this.commands.put(new String[]{""}, new CobblexArg(this.plugin));
    }
}
