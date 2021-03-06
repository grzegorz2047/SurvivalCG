package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.util.ColoringUtil;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;

/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class InfoArg implements Arg {

    private final SCG plugin;

    public InfoArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (args.length >= 2) {
            String guild = args[1].toUpperCase();
            Guild g = plugin.getManager().getGuildManager().getGuilds().get(guild);
            if (g != null) {
                sender.sendMessage(ColoringUtil.colorText("&7============="));
                sender.sendMessage(ColoringUtil.colorText(" "));

                sender.sendMessage(plugin.getManager().getMsgManager().getNoPrefMsg("ginfotit").replace("{GUILD}", g.getGuildTag()));
                //sender.sendMessage(plugin.getManager().getMsgManager().getNoPrefMsg("ginfodesc").replace("{DESCRIPTION}", g.getDescription()));
                sender.sendMessage(plugin.getManager().getMsgManager().getNoPrefMsg("ginfoleader").replace("{LEADER}", g.getLeader()));
                sender.sendMessage(plugin.getManager().getMsgManager().getNoPrefMsg("ginfomemlist").replace("{SIZE}", String.valueOf(g.getMembers().size())).replace("{MEMBERS}", getMembers(g)));
                sender.sendMessage(plugin.getManager().getMsgManager().getNoPrefMsg("ginfoallylist").replace("{SIZE}", String.valueOf(getAlly(g).length())).replace("{MEMBERS}", getAlly(g)));
                sender.sendMessage(ColoringUtil.colorText(" "));

            }else{
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddoesntexists"));
            }
        } else {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("ginfowrongargs"));
        }

    }

    public String getMembers(Guild g) {
        StringBuilder builder = new StringBuilder();
        for (String member : g.getMembers()) {
            builder.append(member);
            builder.append(", ");
        }
        return builder.toString();
    }
    public String getAlly(Guild g) {
        StringBuilder builder = new StringBuilder();
        for (String ally : g.getAlly()) {
            builder.append(ally);
            builder.append(", ");
        }
        return builder.toString();
    }
}
