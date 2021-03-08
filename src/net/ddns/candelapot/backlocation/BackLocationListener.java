package net.ddns.candelapot.backlocation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.ddns.candelapot.Main;
import net.ddns.candelapot.PlayerStatus;

public class BackLocationListener implements Listener
{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) 
	{
		Player player = e.getEntity();
		PlayerStatus status = PlayerStatus.getPlayerStatus(player.getUniqueId());
		if(status.back_loc != null && status.back_task != null)
		{
			status.back_task.cancel();
			status.back_task = null;
		}
		else
		{
			status.back_loc = player.getLocation();
		}
		double loss = Main.db.getBalance(player.getName()) * 0.1f;
		Main.db.withdrawPlayer(player.getName(), loss);
		player.sendMessage(ChatColor.RED + "死亡により " + Main.db.format(loss) + " 失いました");
		if(player.getWorld().getGameRuleValue("keepInventory").equals("true"))
		{
			return;
		}
		Inventory extend = player.getEnderChest();
		World world = player.getWorld();
		for(int i = 0; i < 27; i++)
		{
			ItemStack item = extend.getItem(i);
			if(item != null && item.getType() != Material.AIR && item.getType() != Material.BARRIER)
			{
				world.dropItem(player.getLocation(), item);
				extend.setItem(i, new ItemStack(Material.AIR));
			}
		}
	}
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) 
	{
		Player player = e.getPlayer();
		PlayerStatus status = PlayerStatus.getPlayerStatus(player.getUniqueId());
		if(status.back_loc != null)
		{
			Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable()
			{
				public void run() 
				{
					player.performCommand("tellraw " + player.getName() + " {\"text\":\"" + ChatColor.YELLOW + " * 死亡座標に戻る * \",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/original back\"}}");
				}
			}, 1);
			status.back_task = Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable()
					{
						public void run() 
						{
							status.back_loc = null;
							status.back_task = null;
						}
					}, 20 * 60);
		}
	}
}
