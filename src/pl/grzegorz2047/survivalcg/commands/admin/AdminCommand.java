package pl.grzegorz2047.survivalcg.commands.admin;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.commands.admin.args.HcUnbanArg;
import pl.grzegorz2047.survivalcg.commands.admin.args.ReloadArg;
import pl.grzegorz2047.survivalcg.commands.admin.args.RemoveGuildArg;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class AdminCommand extends BaseWithAliasCommand {
    public AdminCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"unban", "hcub"}, new HcUnbanArg(plugin));
        this.commands.put(new String[]{"gremove", "removeguild", "gusun"}, new RemoveGuildArg(plugin));
        this.commands.put(new String[]{"reload"}, new ReloadArg(plugin));
    }
}
