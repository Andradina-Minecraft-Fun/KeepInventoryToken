package me.alzen.keepinventorytoken.eventlisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PrepareCraftListener implements Listener {
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        for(ItemStack item : event.getInventory().getMatrix()) {
            if(item != null && Objects.requireNonNull(item.getItemMeta()).getDisplayName().equalsIgnoreCase("Keep Inventory Token")) {
                event.getInventory().setResult(null);
                break;
            }
        }
    }
}
