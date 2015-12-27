package pl.grzegorz2047.survivalcg.commands.vip;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.commands.vip.args.VIPArg;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class VIPCommand extends BaseWithAliasCommand {
    public VIPCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{""}, new VIPArg(plugin));
    }
}
