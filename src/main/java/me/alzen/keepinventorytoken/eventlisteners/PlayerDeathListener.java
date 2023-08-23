package me.alzen.keepinventorytoken.eventlisteners;

import me.alzen.keepinventorytoken.KeepInventoryTokenPlugin;
import me.alzen.keepinventorytoken.model.KeepInventoryToken;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

    private KeepInventoryTokenPlugin pluginInstance;

    public PlayerDeathListener(KeepInventoryTokenPlugin instance){
        pluginInstance = instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        PlayerInventory inventory = event.getEntity().getInventory();
        if(checkIfInventoryHasToken(inventory)){
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.setDroppedExp(0);
            event.getDrops().clear();

            inventory.removeItem(new KeepInventoryToken(pluginInstance, 1).getToken());
            event.getEntity().sendMessage(ChatColor.LIGHT_PURPLE + "1 Keep Inventory Token used.");
        }
    }

    private boolean checkIfInventoryHasToken(PlayerInventory inventory){
        for(ItemStack item : inventory.getContents()){
            if(item != null && Objects.requireNonNull(item.getItemMeta()).getDisplayName().equalsIgnoreCase("Keep Inventory Token")){
                return true;
            }
        }
        return false;
    }
}
