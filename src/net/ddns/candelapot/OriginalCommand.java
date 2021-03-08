package net.ddns.candelapot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.inventivetalent.elevator.ParsedSign;

public class OriginalCommand implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if(!(sender instanceof Player)) 
		{
			sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます");
			return true;
		}
		Player player = (Player)sender;
		if(args.length < 1)
		{
			help(player);
			return true;
		}
		switch(args[0])
		{
		case "back":
			if(PlayerStatus.getPlayerStatus(player.getUniqueId()).back_loc == null)
			{
				player.sendMessage(ChatColor.RED + "次の理由のいずれかにより死亡した座標に戻ることができません。");
				player.sendMessage(ChatColor.RED + "・死亡していない");
				player.sendMessage(ChatColor.RED + "・リスポーンしてから1分間が経過している");
				player.sendMessage(ChatColor.RED + "・死亡してから既に1回死亡座標に戻っている");
			}
			else
			{
				player.teleport(PlayerStatus.getPlayerStatus(player.getUniqueId()).back_loc);
				PlayerStatus.getPlayerStatus(player.getUniqueId()).back_loc = null;
			}
			return true;
		case "admin":
			if(args.length < 2)
			{
				helpAdmin(player);
				return true;
			}
			if(player.hasPermission("MyMinecraftServerPlugin.admin"))
			{
				if(args[1].equals("reload"))
				{
					for (Location loc : ParsedSign.signMap.keySet()) 
					{
						if(ParsedSign.signMap.get(loc).disabled)
						{
							player.sendMessage(ChatColor.RED + "使用中のエレベーターが存在するため、リロードできません");
							return true;
						}
					}
					Bukkit.broadcastMessage(ChatColor.AQUA + "==== サーバーのリロードを開始します ====");
					Bukkit.reload();
					Bukkit.broadcastMessage(ChatColor.AQUA + "=== サーバーのリロードが完了しました ===");
					return true;
				}
				return false;
			}
			else
			{
				player.sendMessage(ChatColor.RED + "権限を持っていません");
			}
			return true;
		}
		help(player);
		return true;
	}
	public static void help(Player player)
	{
		ChatColor aqua = ChatColor.AQUA;
		player.sendMessage(aqua + "・拡張インベントリ");
		player.sendMessage(aqua + " 装備のチェストプレートのスロットを右クリックすると");
		player.sendMessage(aqua + " 追加のインベントリをいつでも開くことができます。");
		player.sendMessage(aqua + " 拡張権はOfficialShopの入り口、AdditionalShopで購入可能です。");
		player.sendMessage("");
		player.sendMessage(aqua + "・ブロック補充");
		player.sendMessage(aqua + " ブロックを設置している最中に手に持っているスタックが切れた時");
		player.sendMessage(aqua + " インベントリに同じブロックが存在すれば、自動で手に持ちます。");
		player.sendMessage("");
		player.sendMessage(aqua + "・ブロック複数設置");
		player.sendMessage(aqua + " メインハンドにブレイズロッド、オフハンドに置きたいブロックを持ち");
		player.sendMessage(aqua + " オフハンドに持っているブロックがインベントリに必要な数あれば");
		player.sendMessage(aqua + " 3×3×3の領域に一括でオフハンドに持っているブロックを設置します。");
		player.sendMessage("");
		player.sendMessage(aqua + "・死亡時のペナルティ");
		player.sendMessage(aqua + " 所持金が10%減少します。");
		player.sendMessage("");
		player.sendMessage(aqua + "・死亡した座標に戻る");
		player.sendMessage(aqua + " チャット欄の\"死亡座標に戻る\"をクリックするか、/original back を実行する。");
		player.sendMessage(aqua + " リスポーンしてから1分間の間、1回だけ使用できます。");
	}
	public static void helpAdmin(Player player)
	{
		ChatColor aqua = ChatColor.AQUA;
		player.sendMessage(aqua + "/original admin reload");
		player.sendMessage(aqua + " - サーバーのリロードをします");
	}
}
