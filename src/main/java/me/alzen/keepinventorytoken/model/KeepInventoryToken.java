package me.alzen.keepinventorytoken.model;

import java.util.ArrayList;
import me.alzen.keepinventorytoken.KeepInventoryTokenPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KeepInventoryToken {
    private final ItemStack token;

    public KeepInventoryToken(KeepInventoryTokenPlugin pluginInstance, int quantity){
        FileConfiguration config = pluginInstance.getConfig();

        Material baseMaterial = Material.valueOf(config.getString("BaseMaterial"));
        int customModelData = config.getInt("CustomModelData");
        token = new ItemStack(baseMaterial, quantity);
        ItemMeta itemMeta = token.getItemMeta();

        if(itemMeta != null) {
            itemMeta.setDisplayName("Keep Inventory Token");

            // loop config Lore
            ArrayList<String> lore = new ArrayList<String>();
            config.getStringList("Lore").forEach(str -> {
                lore.add(ChatColor.translateAlternateColorCodes('&', str));
            });

            // add lore to item
            itemMeta.setLore(lore);
            itemMeta.setCustomModelData(customModelData);
        }
        token.setItemMeta(itemMeta);
    }

    public ItemStack getToken(){
        return token;
    }
}
