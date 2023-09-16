package me.alzen.keepinventorytoken.eventlisteners;

import me.alzen.keepinventorytoken.KeepInventoryTokenPlugin;
import me.alzen.keepinventorytoken.model.KeepInventoryToken;
import me.alzen.keepinventorytoken.utils.YamlFileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinEventListener implements Listener {

    private KeepInventoryTokenPlugin instance;
    private FileConfiguration config;
    public PlayerJoinEventListener(KeepInventoryTokenPlugin pluginInstance){
        instance = pluginInstance;
        config = instance.getConfig();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(config.getInt("EnableResourcePack") == 1){
            player.setResourcePack(Objects.requireNonNull(config.getString("ResourcePackURL")));
        }

        if(config.getInt("GiveNewPlayerTokens") == 1){
            UUID uuid = player.getUniqueId();
            if(YamlFileManager.getInt(String.valueOf(uuid)) == 0){
                giveToken(player);
            }


        }
    }

    private void giveToken(Player player){
        String path = String.valueOf(player.getUniqueId());
        if(checkIfInventorySlotAvailable(player)){
            int count = config.getInt("FreeTokenCount");
            player.getInventory().addItem(new KeepInventoryToken(instance, count).getToken());
            player.sendMessage(ChatColor.LIGHT_PURPLE + "You received " + count + " Keep Inventory tokens.");
            YamlFileManager.setData(path, 1);

            player.discoverRecipe(new NamespacedKey(instance, "kitoken"));
        }else{
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Unable to give you Keep Inventory Token. Please free up a slot in your inventory and rejoin.");
            YamlFileManager.setData(path, 0);
        }
    }

    private boolean checkIfInventorySlotAvailable(Player player) {
        for(int slot = 0; slot <= 35; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if(item == null || item.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }

}
