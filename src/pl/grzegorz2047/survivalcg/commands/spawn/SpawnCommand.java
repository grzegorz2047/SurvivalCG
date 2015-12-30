package pl.grzegorz2047.survivalcg.commands.spawn;

import pl.grzegorz2047.api.command.BaseCommand;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.commands.spawn.args.SpawnArg;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class SpawnCommand extends BaseCommand {

    private SCG plugin;

    public SpawnCommand(String basecmd, SCG plugin) {
        super(basecmd);
        this.plugin = plugin;
        this.commands.put("", new SpawnArg(plugin));
    }



}
