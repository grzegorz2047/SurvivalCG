package pl.grzegorz2047.survivalcg.commands.drop.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class DropArg extends Arg{
    private final SCG plugin;

    public DropArg(SCG plugin) {
        this.plugin = plugin;
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(ColoringUtil.colorText("Drop na serwerze:"));
        p.sendMessage(ColoringUtil.colorText("diamenty: %"));
        p.sendMessage(ColoringUtil.colorText("zelazo: %"));
        p.sendMessage(ColoringUtil.colorText("wegiel: %"));
        p.sendMessage(ColoringUtil.colorText("zloto: %"));
        p.sendMessage(ColoringUtil.colorText("redstone: %"));
        p.sendMessage(ColoringUtil.colorText(": %"));
    }

}
