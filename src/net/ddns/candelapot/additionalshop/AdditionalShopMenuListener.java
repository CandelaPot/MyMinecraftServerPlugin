package net.ddns.candelapot.additionalshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import jp.jyn.jecon.db.Database.Reason;
import net.ddns.candelapot.Main;
import net.ddns.candelapot.PlayerStatus;

public class AdditionalShopMenuListener implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) 
	{
		if(!(e.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player player = (Player)e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		if(inv == null)
		{
			return;
		}
		if(!inv.getTitle().equals("Menu"))
		{
			return;
		}
		if(e.getSlot() < 0 || 26 < e.getSlot())
		{
			return;
		}
		e.setCancelled(true);
		PlayerStatus status = PlayerStatus.getPlayerStatus(player.getUniqueId());
		ItemStack item = e.getCurrentItem();
		if(item == null || !item.hasItemMeta())
		{
			return;
		}
		String name = item.getItemMeta().getDisplayName();
		if(name == null)
		{
			return;
		}
		PayItem payitem = PayItem.getPayItem(name);
		if(payitem == null)
		{
			return;
		}
		if(status.has(e.getSlot()) && payitem.isOnce())
		{
			player.sendMessage(ChatColor.RED + "すでに購入済みです");
			return;
		}
		if(Main.db.getBalance(player.getName()) < payitem.getPay())
		{
			player.sendMessage(ChatColor.RED + "お金が不足しています(" + Main.db.format(Main.db.getBalance(player.getName())) + ")");
			return;
		}
		if(e.getSlot() == 7)
		{
			ItemStack boots = player.getInventory().getBoots();
			if(boots == null || boots.getType() == Material.AIR)
			{
				player.sendMessage(ChatColor.RED + "足装備を付けていないため、二段ジャンプオプションを付与することができません");
				return;
			}
			else if(boots.hasItemMeta() && boots.getItemMeta().getLore() != null && boots.getItemMeta().getLore().contains(ChatColor.AQUA + "DoubleJump"))
			{
				player.sendMessage(ChatColor.RED + "足装備に既に二段ジャンプオプションが付与されています");
				return;
			}
		}
		Reason reason = Main.db.withdrawPlayer(player.getName(), payitem.getPay());
		if(reason != Reason.SUCCESS)
		{
			Bukkit.broadcastMessage(ChatColor.RED + "未知のエラーが発生しました、管理者に伝えてください(" + reason.toString() + ")");
			return;
		}
		status.pay(e.getSlot());
		player.sendMessage(ChatColor.YELLOW + (payitem.getItem().hasItemMeta()?payitem.getItem().getItemMeta().getDisplayName():payitem.getItem().getType().toString()) + ChatColor.AQUA + " を購入しました");
		switch(e.getSlot())
		{
		case 0:
		case 1:
		case 2:
			Inventory extend = player.getEnderChest();
			ItemStack air = new ItemStack(Material.AIR);
			int a = e.getSlot() * 9;
			for(int i = a; i < a+9; i++)
			{
				extend.setItem(i, air);
			}
			break;
		case 3:
			player.getWorld().setTime(0);
			break;
		case 4:
			player.getWorld().setTime(14000);
			break;
		case 5:
			player.getWorld().setStorm(false);
			player.getWorld().setThundering(false);
			break;
		case 7:
			ItemMeta meta = player.getInventory().getBoots().getItemMeta();
			List<String> lore = meta.getLore();
			if(lore == null)
			{
				lore = new ArrayList<>();
			}
			lore.add(ChatColor.AQUA + "DoubleJump");
			meta.setLore(lore);
			player.getInventory().getBoots().setItemMeta(meta);
		}
	}
}
