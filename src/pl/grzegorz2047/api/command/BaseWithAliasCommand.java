package pl.grzegorz2047.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Grzegorz2047. 18.09.2015.
 */
public class BaseWithAliasCommand extends BaseCommand {

    Plugin plugin;
    String[] aliases;

    private Map<String[], Arg> commands = new HashMap<String[], Arg>();

    public BaseWithAliasCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd);
        this.plugin = plugin;
        this.aliases = aliases;
        //this.command.put(aliases, new KlasaTypeArg(levels)); example
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Arg argument = null;
        if (args.length != 0) {
            if (cmd.getName().equalsIgnoreCase(baseCmd)) {
                String subCommand = args[0].toLowerCase();//lower case to ensure that all command are correct key
                for (String[] key : commands.keySet()) {
                    for (String alias : key) {
                        if (alias.equals(subCommand)) {
                            argument = commands.get(key);
                            break;
                        }
                    }
                }
                if (argument != null) {
                    argument.execute(sender, args);
                    return true;
                } else {
                    sender.sendMessage("ten argument nie istnieje!");
                    return true;
                }
            }
        }


        return true;
    }
}
