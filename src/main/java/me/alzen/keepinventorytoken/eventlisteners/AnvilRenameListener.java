package me.alzen.keepinventorytoken.eventlisteners;

import me.alzen.keepinventorytoken.KeepInventoryTokenPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

import java.util.Objects;

public class AnvilRenameListener implements Listener {

    KeepInventoryTokenPlugin plugin;

    public AnvilRenameListener(KeepInventoryTokenPlugin instance){
        plugin = instance;
    }
    @EventHandler
    public void onAnvilRename(PrepareAnvilEvent event) {
        String newName = event.getInventory().getRenameText();

        if (newName != null) {  // Check if newName is null before proceeding
            if (newName.equalsIgnoreCase("Keep Inventory Token")) {
                event.setResult(null);
                event.getInventory().setRepairCost(0);
            }
        }


    }
}
