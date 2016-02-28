package cc.isotopestudio.CombinedForce;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandInlay implements CommandExecutor {

	private CombinedForce plugin;

	public CommandInlay(CombinedForce plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("inlay")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			;
			if (item.getAmount() != 1) {
				player.sendMessage(CombinedForce.prefix);
				return true;
			}
			ItemMeta meta = item.getItemMeta();
			int index = 0, pos = -1;
			List<String> lore = item.getItemMeta().getLore();
			try {
				for (String temp : lore) {
					if (temp.contains("镶嵌孔")) {
						pos = index;
						break;
					}
					index++;
				}
			} catch (Exception e) {
				player.sendMessage("不可镶嵌");
				return true;
			}
			if (pos == -1) {
				player.sendMessage("不可镶嵌");
				return true;
			}
			player.sendMessage("POS" + pos);
			ClassGUI GUI = new ClassGUI("宝石镶嵌", 27, new ClassGUI.OptionClickEventHandler() {
				@Override
				public void onOptionClick(ClassGUI.OptionClickEvent event) {
					event.setWillClose(false);
					
				}
			}, plugin, true);
			ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
			ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
			ItemStack yellowGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
			ItemStack orangeGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
			for (int i = 0; i < 27; i++) {
				if (i % 9 == 0 || i % 9 == 8)
					GUI.setOption(i, greyGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
							new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
									.toString());
				if (i % 9 == 1 || i % 9 == 7)
					GUI.setOption(i, blueGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
							new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
									.toString());

				if (i % 9 == 2 || i % 9 == 6)
					GUI.setOption(i, yellowGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
							new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
									.toString());

				if (i % 9 == 3 || i % 9 == 5)
					GUI.setOption(i, orangeGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
							new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
									.toString());
				if (i == 4 || i == 22)
					GUI.setOption(i, orangeGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
							new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
									.toString());

			}
			
			GUI.open(player);
			return true;
		}
		return false;
	}
}
