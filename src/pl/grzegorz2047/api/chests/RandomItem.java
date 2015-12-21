package pl.grzegorz2047.api.chests;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Grzegorz2047. 02.09.2015.
 */
public class RandomItem {
    private Material mat;
    private Enchantment ench;
    private byte abyte = 0;
    private int amount;
    private int level = 1;


    public RandomItem(String data) {
        String[] splits = data.split(":");
        try {
            if (splits.length < 2) {
                System.out.println("Config losowych blokow jest niepoprawny. Cos tu " + data);
                return;
            }
            this.mat = Material.getMaterial(splits[0]);
            if (mat == null) {
                System.out.println("Material " + splits[0] + " jest niepoprawny");
                mat = Material.AIR;
            }
            this.amount = Integer.parseInt(splits[1]);

            if (splits.length == 3) {
                this.abyte = Byte.parseByte(splits[2]);
            } else if (splits.length == 4) {
                this.ench = Enchantment.getByName(splits[3]);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Config losowych blokow jest niepoprawny. Cos tam " + data);
        }
    }


    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(mat, amount, abyte);
        if (ench != null) {
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(ench, level, true);
            item.setItemMeta(meta);
        }
        return item;
    }

}
