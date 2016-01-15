package pl.grzegorz2047.survivalcg.commands.cobblex;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.api.command.Arg;
import pl.grzegorz2047.api.util.MiscUtils;
import pl.grzegorz2047.api.util.RandomUtil;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzegorz2047 on 01.01.2016.
 */
public class CobblexArg implements Arg {
    private final SCG plugin;
    private List<ItemStack> recipe = new ArrayList<ItemStack>();


    public CobblexArg(SCG plugin) {
        this.plugin = plugin;
        ItemStack it = new ItemStack(Material.COBBLESTONE, 1);
        it.setAmount(9*64);
        recipe.add(it);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if (p.getInventory().firstEmpty() > p.getInventory().getSize()) {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("clearinventoryfirst"));
            return;
        }
        if (MiscUtils.hasEnoughItems(recipe, p.getInventory())) {
            MiscUtils.removeRequiredItems(recipe, p.getInventory());
            int randrare = RandomUtil.get().nextInt(100);
            List<ItemStack> ity;
            if (randrare > 80) {
                ity = plugin.getManager().getSettingsManager().getCobblexItemsRare();
            } else {
                ity = plugin.getManager().getSettingsManager().getCobblexItems();

            }
            int rand = RandomUtil.get().nextInt(ity.size() - 2);

            rand += 1;
            ItemStack cItem = ity.get(rand);
            p.getInventory().addItem(cItem.clone());
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("cobblexitemreceived").
                    replace("{ITEM}", cItem.getType().toString()).
                    replace("{SIZE}", cItem.getAmount() + ""));
        } else {
            p.sendMessage(plugin.getManager().getMsgManager().getMsg("notenoughitemforcobblex"));
        }


    }
}
