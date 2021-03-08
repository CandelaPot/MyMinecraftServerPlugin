package net.ddns.candelapot.additionalshop;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.ddns.candelapot.PlayerStatus;
import net.ddns.candelapot.Utility;

public class AdditionalShopFirstListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) 
	{
		Player player = e.getPlayer();
		PlayerStatus status = PlayerStatus.getPlayerStatus(player.getUniqueId());
		if(!status.has(0) && !status.has(1) && !status.has(2))
		{
			Inventory extend = player.getEnderChest();
			ItemStack barrier = Utility.item(ChatColor.RED + "拡張権が必要です", Material.BARRIER);
			if(!extend.contains(Material.BARRIER))
			{
				player.sendMessage(ChatColor.RED + "[重要]インベントリ拡張システムによりエンダーチェストが使えなくなりました。");
				Iterator<ItemStack> i = extend.iterator();
				while(true)
				{
					if(!i.hasNext())
					{
						break;
					}
					ItemStack item = i.next();
					if(item != null && item.getType() != Material.AIR)
					{
						player.sendMessage(ChatColor.RED + "あなたのエンダーチェストにアイテムが入っているのを確認しましたので、すべて取り出して下さい。");
						player.sendMessage(ChatColor.RED + "今の状態でインベントリ拡張権を購入されるとアイテムロストの危険があります。注意してください。");
						return;
					}
				}
				for(int x = 0; x < 27; x++)
				{
					extend.setItem(x, barrier);
				}
			}
		}
	}
}
