package pl.grzegorz2047.survivalcg.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import pl.grzegorz2047.survivalcg.SCG;

import java.util.List;

/**
 * Created by grzegorz2047 on 31.12.2015.
 */
public class RecipeManager {

    private final SCG plugin;


    public RecipeManager(SCG plugin ){
        this.plugin = plugin;
        ItemStack ender = new ItemStack(Material.ENDER_STONE);
        ShapedRecipe enderRecipe = new ShapedRecipe(ender);
        enderRecipe.shape("*S*","SPS","*S*");
        enderRecipe.setIngredient('*', Material.REDSTONE);
        enderRecipe.setIngredient('S', Material.STONE);
        enderRecipe.setIngredient('P', Material.PISTON_BASE);
        Bukkit.addRecipe(enderRecipe);

        ItemStack enderChest = new ItemStack(Material.ENDER_CHEST);
        ShapedRecipe enderChestRecipe = new ShapedRecipe(enderChest);
        enderChestRecipe.shape("***","*P*","***");
        enderChestRecipe.setIngredient('*', Material.OBSIDIAN);
        enderChestRecipe.setIngredient('P', Material.ENDER_PEARL);
        Bukkit.addRecipe(enderChestRecipe);

      //  ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
      //  ShapedRecipe obsidianRecipe = new ShapedRecipe(obsidian);
       // obsidianRecipe.shape("***","***","***");
       // obsidianRecipe.setIngredient('*', Material.COBBLESTONE);
       // Bukkit.addRecipe(obsidianRecipe);
    }

}
