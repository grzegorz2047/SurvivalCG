package pl.grzegorz2047.survivalcg.commands.worldspawn;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.commands.worldspawn.args.SetArg;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class WorldSpawnCommand extends BaseWithAliasCommand {

    public WorldSpawnCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"set"}, new SetArg(plugin));
    }
}
