package pl.grzegorz2047.survivalcg.commands.drop;

import pl.grzegorz2047.api.command.BaseCommand;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.commands.drop.args.DropArg;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class DropCommand extends BaseCommand {

    private final SCG plugin;


    public DropCommand(String basecmd, SCG plugin) {
        super(basecmd);
        this.plugin = plugin;
        this.commands.put("", new DropArg(plugin));
    }
}
