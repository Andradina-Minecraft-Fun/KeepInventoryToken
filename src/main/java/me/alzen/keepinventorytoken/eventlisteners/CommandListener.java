package me.alzen.keepinventorytoken.eventlisteners;

import me.alzen.keepinventorytoken.KeepInventoryTokenPlugin;
import me.alzen.keepinventorytoken.model.KeepInventoryToken;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class CommandListener implements CommandExecutor {

    private KeepInventoryTokenPlugin pluginInstance;

    public CommandListener(KeepInventoryTokenPlugin instance){
        pluginInstance = instance;
    }

    /**
     * keepinventorytoken.give permission
     * 
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String commandName = cmd.getName().toLowerCase();

        // invalid args
        if(args.length != 2) {
           return false;
        }

        // player not found
        Player targetPlayer = (Player)sender.getServer().getPlayer(args[0]);
        if(targetPlayer == null) {
            sender.sendMessage("Player not found");
            return true;
        }

        if(!targetPlayer.isOnline()) {
            sender.sendMessage("Player not online");
            return true;
        }

        // invetory full
        if(!checkIfInventorySlotAvailable(targetPlayer)) {
            sender.sendMessage("Player has inventary full");
            return true;
        }

        // give token
        int count = Integer.valueOf(args[1]);
        targetPlayer.getInventory().addItem(new KeepInventoryToken(pluginInstance, count).getToken());
        targetPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "You received " + count + " Keep Inventory tokens");

        sender.sendMessage("You add " + count + " Keep Inventory tokens to " + targetPlayer.getName());

        return true;
    }

    /**
     * @todo this method is nice. maybe abstract on a helper or a lib
     * 
     * @param player
     * @return
     */
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
