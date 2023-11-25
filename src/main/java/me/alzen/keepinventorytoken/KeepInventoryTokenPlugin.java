package me.alzen.keepinventorytoken;

import me.alzen.keepinventorytoken.eventlisteners.AnvilRenameListener;
import me.alzen.keepinventorytoken.eventlisteners.PlayerDeathListener;
import me.alzen.keepinventorytoken.eventlisteners.PlayerJoinEventListener;
import me.alzen.keepinventorytoken.eventlisteners.PrepareCraftListener;
import me.alzen.keepinventorytoken.model.KeepInventoryToken;
import me.alzen.keepinventorytoken.utils.YamlFileManager;
import me.alzen.keepinventorytoken.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeepInventoryTokenPlugin extends JavaPlugin {

    private final String line = "================================";

    @Override
    public void onEnable() {
        // Plugin startup logic
        int pluginId = 19622;
        Metrics metrics = new Metrics(this, pluginId);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        YamlFileManager.setup();
        YamlFileManager.getConfigurationFile().options().copyDefaults();
        YamlFileManager.save();


        // @todo maybe keep it global, and possible to reload it with command?
        FileConfiguration config = this.getConfig();
        if(config.getBoolean("EnableCraft")) {
            registerTokenRecipe();
        }

        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
        getServer().getPluginManager().registerEvents(new AnvilRenameListener(this), this);
        getServer().getPluginManager().registerEvents(new PrepareCraftListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);

        getLogger().info(line);
        getLogger().info("Keep Inventory Token Plugin Enabled!");
        getLogger().info(line);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        HandlerList.unregisterAll(this);

        getLogger().info(line);
        getLogger().info("Keep Inventory Token Plugin Disabled!");
        getLogger().info(line);
    }

    private void registerTokenRecipe(){
        FileConfiguration config = this.getConfig();

        int count = config.getInt("TokenCountInCrafting");
        ItemStack token = new KeepInventoryToken(this, count).getToken();

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "kitoken"), token);
        recipe.shape(
                "ABC",
                "DEF",
                "GHI"
        );
        recipe.setIngredient('A', Material.valueOf(config.getString("TeleportationTokenRecipe.A")));
        recipe.setIngredient('B', Material.valueOf(config.getString("TeleportationTokenRecipe.B")));
        recipe.setIngredient('C', Material.valueOf(config.getString("TeleportationTokenRecipe.C")));
        recipe.setIngredient('D', Material.valueOf(config.getString("TeleportationTokenRecipe.D")));
        recipe.setIngredient('E', Material.valueOf(config.getString("TeleportationTokenRecipe.E")));
        recipe.setIngredient('F', Material.valueOf(config.getString("TeleportationTokenRecipe.F")));
        recipe.setIngredient('G', Material.valueOf(config.getString("TeleportationTokenRecipe.G")));
        recipe.setIngredient('H', Material.valueOf(config.getString("TeleportationTokenRecipe.H")));
        recipe.setIngredient('I', Material.valueOf(config.getString("TeleportationTokenRecipe.I")));
        Bukkit.addRecipe(recipe);
    }
}
