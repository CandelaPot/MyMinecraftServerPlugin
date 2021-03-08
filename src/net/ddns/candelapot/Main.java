package net.ddns.candelapot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.elevator.listener.EntityListener;
import org.inventivetalent.elevator.listener.SignListener;

import jp.jyn.jecon.Jecon;
import jp.jyn.jecon.db.Database;
import net.ddns.candelapot.additionalshop.AdditionalShopFirstListener;
import net.ddns.candelapot.additionalshop.AdditionalShopMenuListener;
import net.ddns.candelapot.additionalshop.AdditionalShopOpenListener;
import net.ddns.candelapot.autorefill.AutoRefillListener;
import net.ddns.candelapot.backlocation.BackLocationListener;
import net.ddns.candelapot.clickfill.ClickFillListener;
import net.ddns.candelapot.custominventory.CustomInventoryListener;
import net.ddns.candelapot.doublejump.DoubleJumpFlyListener;
import net.ddns.candelapot.doublejump.DoubleJumpResetListener;
import net.ddns.candelapot.enchantshop.ChangeStoneListener;
import net.ddns.candelapot.enchantshop.EnchantShopListener;
import net.ddns.candelapot.pricefluctuate.PriceFluctuateListener;

public class Main extends JavaPlugin implements Listener
{
	public static Main instance;
	
	public static Database db;
	
	public static final String RAW_SIGN_TITLE = "[Elevator]";
	public static String SIGN_TITLE = "§6[§5Elevator§6]";
	public static boolean PRIVATE_ELEVATORS = true;
	public static boolean TRIGGER_REDSTONE = true;
	public static boolean DISBLE_DAMAGE = false;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		db = ((Jecon)Bukkit.getPluginManager().getPlugin("Jecon")).getDb();
		
		this.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new AdditionalShopFirstListener(), this);
		Bukkit.getPluginManager().registerEvents(new AdditionalShopMenuListener(), this);
		Bukkit.getPluginManager().registerEvents(new AdditionalShopOpenListener(), this);
		Bukkit.getPluginManager().registerEvents(new AutoRefillListener(), this);
		Bukkit.getPluginManager().registerEvents(new BackLocationListener(), this);
		Bukkit.getPluginManager().registerEvents(new ClickFillListener(), this);
		Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new DoubleJumpFlyListener(), this);
		Bukkit.getPluginManager().registerEvents(new DoubleJumpResetListener(), this);
		Bukkit.getPluginManager().registerEvents(new EnchantShopListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChangeStoneListener(), this);
		Bukkit.getPluginManager().registerEvents(new PriceFluctuateListener(), this);
		Bukkit.getPluginManager().registerEvents(new SignListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		
		this.getCommand("OriginalCommand").setExecutor(new OriginalCommand());
		
		initRecipe();
		PlayerStatus.init();
		EnchantShopListener.initEnchant();
		AdditionalShopOpenListener.initMenu();
		EnchantShopListener.initMenu();
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			DoubleJumpResetListener.cooldown.put(player.getUniqueId(), true);
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				player.setAllowFlight(false);
			}
		}
	}
	@Override
	public void onDisable() 
	{
		PlayerStatus.save();
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) 
	{
		Player player = e.getPlayer();
		player.sendMessage(ChatColor.AQUA + "===== サーバー更新情報 =====");
		player.sendMessage(ChatColor.AQUA + "・[/home]と[/home set]を実行する際、" + db.format(100.0) + " 消費するよう変更");
		player.sendMessage(ChatColor.AQUA + "・[/money top <数値>]を使用可能に変更");
		player.sendMessage(ChatColor.AQUA + "・AdditionalShopに[朝にする]と[晴れにする]を追加");
		player.sendMessage(ChatColor.AQUA + "・-------------------------------");
		player.sendMessage(ChatColor.AQUA + "・Backコマンド(死亡座標に戻る機能)実装");
		player.sendMessage(ChatColor.AQUA + "・keepInventoryをオンに設定");
		player.sendMessage(ChatColor.AQUA + "・死亡時、所持金の10％を消失");
		player.sendMessage(ChatColor.AQUA + "");
		player.sendMessage(ChatColor.AQUA + "自作プラグインの機能一覧については /original");
		
		DoubleJumpResetListener.cooldown.put(player.getUniqueId(), true);
	}
	
	@SuppressWarnings("deprecation")
	public static void initRecipe()
	{
		ItemStack kakou = new ItemStack(Material.STONE, 1, (byte)1);
		ShapedRecipe kakou_r1 = new ShapedRecipe(kakou);
		kakou_r1.shape("A", "A");
		kakou_r1.setIngredient('A', Material.STONE, 3);
		Bukkit.getServer().addRecipe(kakou_r1);
		
		ShapedRecipe kakou_r2 = new ShapedRecipe(kakou);
		kakou_r2.shape("AA");
		kakou_r2.setIngredient('A', Material.STONE, 5);
		Bukkit.getServer().addRecipe(kakou_r2);
		
		ItemStack senryoku = new ItemStack(Material.STONE, 1, (byte)3);
		ShapedRecipe senryoku_r1 = new ShapedRecipe(senryoku);
		senryoku_r1.shape("A", "A");
		senryoku_r1.setIngredient('A', Material.STONE, 5);
		Bukkit.getServer().addRecipe(senryoku_r1);
		
		ShapedRecipe senryoku_r2 = new ShapedRecipe(senryoku);
		senryoku_r2.shape("AA");
		senryoku_r2.setIngredient('A', Material.STONE, 1);
		Bukkit.getServer().addRecipe(senryoku_r2);
		
		ItemStack anzan = new ItemStack(Material.STONE, 1, (byte)5);
		ShapedRecipe anzan_r1 = new ShapedRecipe(anzan);
		anzan_r1.shape("A", "A");
		anzan_r1.setIngredient('A', Material.STONE, 1);
		Bukkit.getServer().addRecipe(anzan_r1);
		
		ShapedRecipe anzan_r2 = new ShapedRecipe(anzan);
		anzan_r2.shape("AA");
		anzan_r2.setIngredient('A', Material.STONE, 3);
		Bukkit.getServer().addRecipe(anzan_r2);
	}
	
}
