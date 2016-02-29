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
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("��û��Ȩ��").toString());
				return true;
			}
			ItemStack item = player.getItemInHand();
			;
			if (item.getAmount() != 1) {
				player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("������Ʒ��������Ϊ1��")
						.toString());
				return true;
			}
			int index = 0, pos = -1;
			List<String> lore = item.getItemMeta().getLore();
			try {
				for (String temp : lore) {
					if (temp.contains("��Ƕ��")) {
						pos = index;
						break;
					}
					index++;
				}
			} catch (Exception e) {
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����Ʒ�޷���Ƕ��ʯ").toString());
				return true;
			}
			if (pos == -1) {
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����Ʒ�޷���Ƕ��ʯ").toString());
				return true;
			}
			ClassGUI GUI = new ClassGUI("��ʯ��Ƕ", 27, pos, plugin);

			GUI.open(player);
			return true;
		}
		return false;
	}
}
