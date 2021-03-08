package net.ddns.candelapot.enchantshop;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ddns.candelapot.Main;
import net.ddns.candelapot.Utility;
import net.minecraft.server.v1_11_R1.Enchantment;
import net.minecraft.server.v1_11_R1.EnumHand;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.MinecraftKey;

public class EnchantShopListener implements Listener
{
	public static Inventory shop1;
	public static Inventory shop2;
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) 
	{
		Entity entity = e.getRightClicked();
		if(entity.getType() != EntityType.VILLAGER)
		{
			return;
		}
		if(entity.getCustomName() == null || !entity.getCustomName().equals("エンチャント屋"))
		{
			return;
		}
		e.setCancelled(true);
		ItemStack main = e.getPlayer().getInventory().getItemInMainHand();
		if(main == null)
		{
			e.getPlayer().sendMessage(ChatColor.RED + "エンチャントしたいアイテムを手に持って実行してください");
			return;
		}
		e.getPlayer().openInventory(shop1);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE)
		{
			e.setCancelled(true);
			e.getPlayer().openInventory(shop1);
		}
	}
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
		if(inv.getTitle().equals("Enchant Shop"))
		{
			e.setCancelled(true);
			if(e.getSlot() < 0 || 53 < e.getSlot())
			{
				return;
			}
			if(player.getInventory().getItemInMainHand() == null)
			{
				player.sendMessage(ChatColor.RED + "エンチャントしたいアイテムを手に持って実行してください");
				return;
			}
			int xp = 0;
			Enchantment enchant = null;
			switch(e.getSlot())
			{
			case 0:
				xp = 5;
				break;
			case 1:
				xp = 10;
				break;
			case 2:
				xp = 15;
				break;
			case 3:
				xp = 20;
				break;
			case 4:
				xp = 25;
				break;
			case 5:
				xp = 30;
				break;
			case 6:
				xp = 35;
				break;
			case 7:
				xp = 40;
				break;
			case 18:
				enchant = getEnchantById(0);
				break;
			case 19:
				enchant = getEnchantById(1);
				break;
			case 20:
				enchant = getEnchantById(2);
				break;
			case 21:
				enchant = getEnchantById(3);
				break;
			case 22:
				enchant = getEnchantById(4);
				break;
			case 23:
				enchant = getEnchantById(5);
				break;
			case 24:
				enchant = getEnchantById(6);
				break;
			case 25:
				enchant = getEnchantById(7);
				break;
			case 26:
				enchant = getEnchantById(8);
				break;
			case 27:
				enchant = getEnchantById(16);
				break;
			case 28:
				enchant = getEnchantById(17);
				break;
			case 29:
				enchant = getEnchantById(18);
				break;
			case 30:
				enchant = getEnchantById(19);
				break;
			case 31:
				enchant = getEnchantById(20);
				break;
			case 32:
				enchant = getEnchantById(21);
				break;
			case 33:
				enchant = getEnchantById(22);
				break;
			case 34:
				enchant = getEnchantById(32);
				break;
			case 35:
				enchant = getEnchantById(33);
				break;
			case 36:
				enchant = getEnchantById(34);
				break;
			case 37:
				enchant = getEnchantById(35);
				break;
			case 38:
				enchant = getEnchantById(48);
				break;
			case 39:
				enchant = getEnchantById(49);
				break;
			case 40:
				enchant = getEnchantById(50);
				break;
			case 41:
				enchant = getEnchantById(51);
				break;
			case 42:
				enchant = getEnchantById(61);
				break;
			case 43:
				enchant = getEnchantById(62);
				break;
			case 200:
				enchant = getEnchantById(200);
				break;
			}
			if(xp != 0)
			{
				ItemStack main = player.getInventory().getItemInMainHand();
				net.minecraft.server.v1_11_R1.ItemStack main2 = ((CraftPlayer)player).getHandle().b(EnumHand.MAIN_HAND);
				if(main == null || main.getType() == Material.AIR)
				{
					player.sendMessage(ChatColor.RED + "エンチャントしたいアイテムを手に持って実行してください");
					return;
				}
				if(player.getLevel() < xp)
				{
					player.sendMessage(ChatColor.RED + "レベルが足りません");
					return;
				}
				List<Enchantment> enchants = getEnchantList(main2);
				if(main.getType() == Material.BOOK || enchants.isEmpty())
				{
					player.sendMessage(ChatColor.RED + "このアイテムはエンチャント出来ません");
					return;
				}
				if(main.hasItemMeta() && main.getItemMeta().hasEnchants())
				{
					player.sendMessage(ChatColor.RED + "すでにエンチャントがついているため、エンチャント出来ません");
					return;
				}
				List<String> add = executeEnchant(enchants, xp, main2);
				ItemMeta meta = main.getItemMeta();
				List<String> lore = meta.getLore();
				if(lore == null)
				{
					lore = add;
				}
				else
				{
					lore.addAll(add);
				}
				meta.setLore(lore);
				main.setItemMeta(meta);
				player.setLevel(player.getLevel() - xp);
				player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
				player.closeInventory();
			}
			else if(enchant != null)
			{
				player.sendMessage(ChatColor.RED + "まだ実装してないよ♪");
			}
		}
		else if(inv.getTitle().equals("エンチャントオプション"))
		{
			if(e.getSlot() < 0 || 53 < e.getSlot())
			{
				return;
			}
		}
		else
		{
			return;
		}
		e.setCancelled(true);
	}
	public static List<Enchantment> getEnchantList(net.minecraft.server.v1_11_R1.ItemStack item)
	{
		List<Enchantment> result = new ArrayList<>();
		;
		for(Iterator<Enchantment> ite = Enchantment.enchantments.iterator(); ite.hasNext();)
		{
			Enchantment enchant = ite.next();
			if(!enchant.isTreasure() && enchant.canEnchant(item))
			{
				result.add(enchant);
			}
		}
		Collections.shuffle(result);
		return result;
	}
	public static ItemStack getEnchantItem(ItemStack item, double pay)
	{
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null)
		{
			lore = new ArrayList<>();
		}
		lore.add(ChatColor.YELLOW + "値段: " + Main.db.format(pay));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public static List<String> executeEnchant(List<Enchantment> enchants, int xp, net.minecraft.server.v1_11_R1.ItemStack item)
	{
		List<String> result = new ArrayList<>();
		List<Enchant> success = new ArrayList<>();
		Random rnd = new Random();
		int random;
		boolean flag;
		for(Enchantment enchant : enchants)
		{
			flag = false;
			if(!success.isEmpty())
			{
				for(Enchant loop : success)
				{
					if(!enchant.c(loop.getEnchant()))
					{
						flag = true;
						break;
					}
				}
			}
			if(flag)
			{
				continue;
			}
			random = rnd.nextInt(1000)+1;
			switch(xp)
			{
			case 5:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 100)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					
				}
				break;
			case 10:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 600)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 600)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					
				}
				break;
			case 15:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 800)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 700)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 600)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 600)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					
				}
				break;
			case 20:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 600)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 4));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 600)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 650)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 700)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 800)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 800)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 700)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					if(random <= 50)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					
				}
				break;
			case 25:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 4));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 350)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 800)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 700)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 750)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					if(random <= 100)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					
				}
				break;
			case 30:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 500)
					{
						success.add(new Enchant(enchant, 4));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 5));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 500)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 550)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 600)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 800)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 550)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					if(random <= 150)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					if(random <= 50)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				break;
			case 35:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 4));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 5));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 350)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 400)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 800)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 350)
					{
						success.add(new Enchant(enchant, 1));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 2));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					if(random <= 100)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				break;
			case 40:
				if(contain(enchant, 4, Rarity.UNCOMMON))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 5));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 6));
					}
				}
				else if(contain(enchant, 3, Rarity.UNCOMMON))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 4));
					}
				}
				else if(contain(enchant, 3, Rarity.RARE))
				{
					if(random <= 550)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 4));
					}
				}
				else if(contain(enchant, 3, Rarity.VERY_RARE))
				{
					if(random <= 600)
					{
						success.add(new Enchant(enchant, 3));
					}
					else if(random <= 800)
					{
						success.add(new Enchant(enchant, 4));
					}
				}
				else if(contain(enchant, 2, Rarity.UNCOMMON))
				{
					if(random <= 500)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 900)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 2, Rarity.RARE))
				{
					if(random <= 550)
					{
						success.add(new Enchant(enchant, 2));
					}
					else if(random <= 850)
					{
						success.add(new Enchant(enchant, 3));
					}
				}
				else if(contain(enchant, 1, Rarity.RARE))
				{
					if(random <= 300)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				else if(contain(enchant, 1, Rarity.VERY_RARE))
				{
					if(random <= 200)
					{
						success.add(new Enchant(enchant, 1));
					}
				}
				break;
			}
		}
		for(Enchant loop : success)
		{
			switch(Enchantment.getId(loop.getEnchant()))
			{
			case 200:
				result.add(ChatColor.GRAY + "石変換");
				break;
			}
			item.addEnchantment(loop.getEnchant(), loop.getLevel());
		}
		return result;
	}
	public static void initMenu()
	{
		shop1 = Bukkit.createInventory(null, 54, "Enchant Shop");
		shop1.setItem(0, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル5エンチャント", Material.ENCHANTMENT_TABLE), (short)5).getItem());
		shop1.setItem(1, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル10エンチャント", Material.ENCHANTMENT_TABLE), (short)10).getItem());
		shop1.setItem(2, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル15エンチャント", Material.ENCHANTMENT_TABLE), (short)15).getItem());
		shop1.setItem(3, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル20エンチャント", Material.ENCHANTMENT_TABLE), (short)20).getItem());
		shop1.setItem(4, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル25エンチャント", Material.ENCHANTMENT_TABLE), (short)25).getItem());
		shop1.setItem(5, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル30エンチャント", Material.ENCHANTMENT_TABLE), (short)30).getItem());
		shop1.setItem(6, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル35エンチャント", Material.ENCHANTMENT_TABLE), (short)35).getItem());
		shop1.setItem(7, new PayEnchant(Utility.item(ChatColor.WHITE + "レベル40エンチャント", Material.ENCHANTMENT_TABLE), (short)40).getItem());
		shop1.setItem(18, getEnchantItem(Utility.item(ChatColor.WHITE + "ダメージ軽減", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(19, getEnchantItem(Utility.item(ChatColor.WHITE + "火炎耐性", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(20, getEnchantItem(Utility.item(ChatColor.WHITE + "落下耐性", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(21, getEnchantItem(Utility.item(ChatColor.WHITE + "爆発耐性", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(22, getEnchantItem(Utility.item(ChatColor.WHITE + "飛び道具耐性", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(23, getEnchantItem(Utility.item(ChatColor.WHITE + "水中呼吸", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(24, getEnchantItem(Utility.item(ChatColor.WHITE + "水中採掘", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(25, getEnchantItem(Utility.item(ChatColor.WHITE + "棘の鎧", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(26, getEnchantItem(Utility.item(ChatColor.WHITE + "水中歩行", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(27, getEnchantItem(Utility.item(ChatColor.WHITE + "ダメージ増加", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(28, getEnchantItem(Utility.item(ChatColor.WHITE + "アンデッド特攻", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(29, getEnchantItem(Utility.item(ChatColor.WHITE + "虫特攻", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(30, getEnchantItem(Utility.item(ChatColor.WHITE + "ノックバック", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(31, getEnchantItem(Utility.item(ChatColor.WHITE + "火属性", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(32, getEnchantItem(Utility.item(ChatColor.WHITE + "ドロップ増加", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(33, getEnchantItem(Utility.item(ChatColor.WHITE + "範囲ダメージ増加", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(34, getEnchantItem(Utility.item(ChatColor.WHITE + "効率強化", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(35, getEnchantItem(Utility.item(ChatColor.WHITE + "シルクタッチ", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(36, getEnchantItem(Utility.item(ChatColor.WHITE + "耐久力", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(37, getEnchantItem(Utility.item(ChatColor.WHITE + "幸運", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(38, getEnchantItem(Utility.item(ChatColor.WHITE + "射撃ダメージ増加", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(39, getEnchantItem(Utility.item(ChatColor.WHITE + "パンチ", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(40, getEnchantItem(Utility.item(ChatColor.WHITE + "フレイム", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(41, getEnchantItem(Utility.item(ChatColor.WHITE + "無限", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(42, getEnchantItem(Utility.item(ChatColor.WHITE + "宝釣り", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(43, getEnchantItem(Utility.item(ChatColor.WHITE + "入れ食い", Material.ENCHANTED_BOOK), 0));
		shop1.setItem(44, getEnchantItem(Utility.item(ChatColor.WHITE + "石変換", Material.ENCHANTED_BOOK), 0));
		
		shop2 = Bukkit.createInventory(null, 27, "Enchant Option");
		shop2.setItem(0, getEnchantItem(Utility.item(ChatColor.WHITE + "レベル5エンチャント", Material.ENCHANTMENT_TABLE), 0));
	}
	public static void initEnchant()
	{
		Enchantment.enchantments.a(200, new MinecraftKey("change_stone"), new ChangeStone(Enchantment.Rarity.RARE, new EnumItemSlot[]{EnumItemSlot.MAINHAND}));
		try 
		{
			Field f = org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment((Enchantment)Enchantment.enchantments.get(new MinecraftKey("change_stone"))));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static Enchantment getEnchantById(int id)
	{
		Enchantment result = null;
		switch(id)
		{
		case 0:
			result = Enchantment.enchantments.get(new MinecraftKey("protection"));
			break;
		case 1:
			result = Enchantment.enchantments.get(new MinecraftKey("fire_protection"));
			break;
		case 2:
			result = Enchantment.enchantments.get(new MinecraftKey("feather_falling"));
			break;
		case 3:
			result = Enchantment.enchantments.get(new MinecraftKey("blast_protection"));
			break;
		case 4:
			result = Enchantment.enchantments.get(new MinecraftKey("projectile_protection"));
			break;
		case 5:
			result = Enchantment.enchantments.get(new MinecraftKey("respiration"));
			break;
		case 6:
			result = Enchantment.enchantments.get(new MinecraftKey("aqua_affinity"));
			break;
		case 7:
			result = Enchantment.enchantments.get(new MinecraftKey("thorns"));
			break;
		case 8:
			result = Enchantment.enchantments.get(new MinecraftKey("depth_strider"));
			break;
		case 16:
			result = Enchantment.enchantments.get(new MinecraftKey("sharpness"));
			break;
		case 17:
			result = Enchantment.enchantments.get(new MinecraftKey("smite"));
			break;
		case 18:
			result = Enchantment.enchantments.get(new MinecraftKey("bane_of_arthropods"));
			break;
		case 19:
			result = Enchantment.enchantments.get(new MinecraftKey("knockback"));
			break;
		case 20:
			result = Enchantment.enchantments.get(new MinecraftKey("fire_aspect"));
			break;
		case 21:
			result = Enchantment.enchantments.get(new MinecraftKey("looting"));
			break;
		case 22:
			result = Enchantment.enchantments.get(new MinecraftKey("sweeping"));
			break;
		case 32:
			result = Enchantment.enchantments.get(new MinecraftKey("efficiency"));
			break;
		case 33:
			result = Enchantment.enchantments.get(new MinecraftKey("silk_touch"));
			break;
		case 34:
			result = Enchantment.enchantments.get(new MinecraftKey("unbreaking"));
			break;
		case 35:
			result = Enchantment.enchantments.get(new MinecraftKey("fortune"));
			break;
		case 48:
			result = Enchantment.enchantments.get(new MinecraftKey("power"));
			break;
		case 49:
			result = Enchantment.enchantments.get(new MinecraftKey("punch"));
			break;
		case 50:
			result = Enchantment.enchantments.get(new MinecraftKey("flame"));
			break;
		case 51:
			result = Enchantment.enchantments.get(new MinecraftKey("infinity"));
			break;
		case 61:
			result = Enchantment.enchantments.get(new MinecraftKey("luck_of_the_sea"));
			break;
		case 62:
			result = Enchantment.enchantments.get(new MinecraftKey("lure"));
			break;
		case 200:
			result = Enchantment.enchantments.get(new MinecraftKey("change_stone"));
			break;
		}
		return result;
	}
	public static boolean contain(Enchantment enchant, int max, Rarity rarity)
	{
		int[] list = null;
		switch(max)
		{
		case 1:
			switch(rarity)
			{
			case UNCOMMON:
				break;
			case RARE:
				list = new int[]{6, 33, 50, 200};
				break;
			case VERY_RARE:
				list = new int[]{51};
				break;
			}
			break;
		case 2:
			switch(rarity)
			{
			case UNCOMMON:
				list = new int[]{19};
				break;
			case RARE:
				list = new int[]{20, 49};
				break;
			case VERY_RARE:
				break;
			}
			break;
		case 3:
			switch(rarity)
			{
			case UNCOMMON:
				list = new int[]{34};
				break;
			case RARE:
				list = new int[]{5, 8, 22, 61, 62};
				break;
			case VERY_RARE:
				list = new int[]{7, 21, 35};
				break;
			}
			break;
		case 4:
			switch(rarity)
			{
			case UNCOMMON:
				list = new int[]{0, 1, 2, 3, 4, 16, 17, 18, 32, 48};
				break;
			case RARE:
				break;
			case VERY_RARE:
				break;
			}
			break;
		}
		if(list == null)
		{
			return false;
		}
		else
		{
			for(int loop : list)
			{
				if(enchant.equals(getEnchantById(loop)))
				{
					return true;
				}
			}
		}
		return false;
	}
	public static enum Rarity
	{
		UNCOMMON, RARE, VERY_RARE;
	}
}
