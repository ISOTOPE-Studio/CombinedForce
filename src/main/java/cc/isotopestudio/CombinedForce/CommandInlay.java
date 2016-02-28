package cc.isotopestudio.CombinedForce;

import java.util.List;

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
			ItemStack item;
			try {
				item = player.getItemInHand();
			} catch (Exception e) {
				return true;
			}
			if (item.getAmount() != 1) {
				player.sendMessage(CombinedForce.prefix);
				return true;
			}
			ItemMeta meta = item.getItemMeta();
			int index = 0, pos = -1;
			List<String> lore = item.getItemMeta().getLore();
			for (String temp : lore) {
				if (temp.contains("ÏâÇ¶¿×")) {
					pos = index;
					break;
				}
				index++;
			}
			if (pos == -1) {
				return true;
			}
			player.sendMessage("POS" + pos);
			return true;
		}
		return false;
	}
}
