package net.ddns.candelapot.doublejump;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import net.ddns.candelapot.Main;

public class DoubleJumpResetListener implements Listener
{
	public static Map<UUID, Boolean> cooldown = new HashMap<>();
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) 
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		if (!player.isOnGround() && player.getAllowFlight() && player.getFallDistance() > 2) {
		    Location to = e.getTo().clone().subtract(0, 0.0001, 0); // Get the location they will be at next tick
		    if (to.getBlock().getType() != Material.AIR) 
		    {// If that block is not air
		    	cooldown.put(player.getUniqueId(), true);
		        player.setAllowFlight(false); // Cancel their ability to fly so that they take regular fall damage
		    }
		}
		if(cooldown.containsKey(player.getUniqueId()) && player.isOnGround() && !player.getAllowFlight())
		{
			ItemStack boots = player.getInventory().getBoots();
			if(boots != null && boots.hasItemMeta() && boots.getItemMeta().getLore().contains(ChatColor.AQUA + "DoubleJump"))
			{
				Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable()
						{
							public void run() 
							{
								player.setAllowFlight(true);
								cooldown.remove(player.getUniqueId());
							}
						}, 2);
			}
		}
	}
}
