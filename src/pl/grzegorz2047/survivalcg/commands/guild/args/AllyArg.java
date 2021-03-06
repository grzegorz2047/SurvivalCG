package pl.grzegorz2047.survivalcg.commands.guild.args;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.user.User;
import pl.grzegorz2047.survivalcg.SCG;
import pl.grzegorz2047.survivalcg.guild.Guild;
import pl.grzegorz2047.survivalcg.guild.relation.Relation;

/**
 * Created by grzegorz2047 on 29.12.2015.
 */
public class AllyArg implements Arg {
    private final SCG plugin;

    public AllyArg(Plugin plugin) {
        this.plugin = (SCG) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getManager().getMsgManager().getMsg("cmdonlyforplayer"));
            return;
        }
        Player p = (Player) sender;
        if (args.length >= 2) {
            String guildToCheck = args[1].toUpperCase();
            Guild guild = plugin.getManager().getGuildManager().getGuilds().get(guildToCheck);
            if (guild == null) {
                sender.sendMessage(plugin.getManager().getMsgManager().getMsg("guilddoesntexists"));
                return;
            }
            User user = plugin.getManager().getUserManager().getUsers().get(p.getName());
            Guild requestingGuild = user.getGuild();
            if (requestingGuild == null) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("notinguild"));
                return;
            }
            if (!requestingGuild.getLeader().equals(p.getName())) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("playernotleader"));
                return;
            }

            if (guild.equals(requestingGuild)) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("allyyourselferror"));
                return;
            }
            Player leader = Bukkit.getPlayer(guild.getLeader());
            if (leader == null) {
                p.sendMessage(plugin.getManager().getMsgManager().getMsg("leadernotonline"));
                return;
            }
            for (Relation r : plugin.getManager().getGuildManager().getPendingRelations()) {
                if (r.getWithWho().equals(requestingGuild.getGuildTag())) {


                    r.setExpired(true);
                    plugin.getManager().getMysqlManager().getRelationQuery().addRelation(requestingGuild, guild);
                    guild.getAlly().add(requestingGuild.getGuildTag());
                    requestingGuild.getAlly().add(guild.getGuildTag());
                    Bukkit.broadcastMessage(plugin.getManager().getMsgManager().getMsg("broadcast-ally")
                            .replace("{GUILD1}", guild.getGuildTag())
                            .replace("{GUILD2}", requestingGuild.getGuildTag()));
                    plugin.getManager().getScoreboardTagsManager().setRelationTag(guild,requestingGuild);
                    return;
                }
            }
            leader.sendMessage(plugin.getManager().getMsgManager().getMsg("sentallyrequest"));
            leader.sendMessage(plugin.getManager().getMsgManager().getMsg("toacceptallymsg").replace("{GUILD}", requestingGuild.getGuildTag()));
            plugin.getManager().getGuildManager().requestAlly(requestingGuild, guild);
        } else {
            sender.sendMessage("/g ally <guild>");
        }
    }
}
