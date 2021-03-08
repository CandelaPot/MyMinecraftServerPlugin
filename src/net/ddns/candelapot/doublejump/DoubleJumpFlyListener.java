package net.ddns.candelapot.doublejump;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DoubleJumpFlyListener implements Listener
{
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent e) 
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
		{
			return;
		}
		e.setCancelled(true);
		DoubleJumpResetListener.cooldown.put(player.getUniqueId(), true);
		player.setFlying(false);
		player.setAllowFlight(false);
		ItemStack boots = player.getInventory().getBoots();
		if(boots != null && boots.hasItemMeta() && boots.getItemMeta().getLore().contains(ChatColor.AQUA + "DoubleJump"))
		{
			Vector vec = player.getLocation().getDirection();
			vec.setY(1.0);
			vec = vec.multiply(0.5);
			player.setVelocity(vec);
			player.setFallDistance(player.getFallDistance() >= 1.0f ? player.getFallDistance() - 1.0f : 0.0f);
		}
	}
}
