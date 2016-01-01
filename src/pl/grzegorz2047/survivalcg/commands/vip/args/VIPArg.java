package pl.grzegorz2047.survivalcg.commands.vip.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;

/**
 * Created by grzegorz2047 on 27.12.2015.
 */
public class VIPArg extends Arg {
    private final SCG plugin;

    public VIPArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(ColoringUtil.colorText("&7Funkcje dla VIPa:"));
        p.sendMessage(ColoringUtil.colorText("&3 -&7 Brak bana przy smierci"));
        p.sendMessage(ColoringUtil.colorText("&3 -&7 komenda /repair"));
        p.sendMessage(ColoringUtil.colorText("&3 -&7 komenda /kit vip"));
    }
}
