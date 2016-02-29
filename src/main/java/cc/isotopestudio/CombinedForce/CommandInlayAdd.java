package cc.isotopestudio.CombinedForce;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandInlayAdd implements CommandExecutor {

	private CombinedForce plugin;

	public CommandInlayAdd(CombinedForce plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("inlayadd")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			Player player = (Player) sender;
			if (!player.hasPermission("CombinedForce.add")) {
				player.sendMessage(
						(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("��û��Ȩ��").toString());
				return true;
			}
			ItemStack item = player.getItemInHand();

			if (item.getAmount() != 1) {
				player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("������Ʒ��������Ϊ1��")
						.toString());
				return true;
			}
			ItemMeta itemMeta = item.getItemMeta();
			List<String> lore = itemMeta.getLore();
			if (lore == null) {
				lore = new ArrayList<String>();
			}
			lore.add(plugin.getConfig().getString("add.lore"));
			itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
			player.sendMessage(
					(new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("�ɹ����ף�").toString());
			return true;
		}
		return false;
	}
}
