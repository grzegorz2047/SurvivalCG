package pl.grzegorz2047.survivalcg.commands.guild;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.survivalcg.commands.guild.args.*;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class GuildCommand extends BaseWithAliasCommand {

    public GuildCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"zaloz", "stworz", "create"}, new CreateArg(plugin));
        this.commands.put(new String[]{"usun", "zamknij", "delete", "close"}, new DeleteArg(plugin));
        this.commands.put(new String[]{"akceptuj", "dolacz", "accept", "join"}, new AcceptArg(plugin));
        this.commands.put(new String[]{"odmow", "odrzuc", "deny", "decline"}, new DenyArg(plugin));
        this.commands.put(new String[]{"dom", "baza", "home", "base"}, new HomeArg(plugin));
        this.commands.put(new String[]{"opusc", "wyjdz", "leave", "out"}, new LeaveArg(plugin));
        this.commands.put(new String[]{"pomoc", "help", "h", "?", ""}, new LeaveArg(plugin));
    }
}
