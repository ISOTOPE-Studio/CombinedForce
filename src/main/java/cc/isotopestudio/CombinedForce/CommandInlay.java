package cc.isotopestudio.CombinedForce;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
			if (!player.hasPermission("CombinedForce.use")) {
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("你没有权限").toString());
				return true;
			}
			ItemStack item = player.getItemInHand();
			;
			if (item.getAmount() != 1) {
				player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("手中物品数量必须为1个")
						.toString());
				return true;
			}
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
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无法镶嵌宝石").toString());
				return true;
			}
			if (pos == -1) {
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无法镶嵌宝石").toString());
				return true;
			}
			ClassGUI GUI = new ClassGUI("宝石镶嵌", 27, pos, plugin);

			GUI.open(player);
			return true;
		}
		return false;
	}
}
