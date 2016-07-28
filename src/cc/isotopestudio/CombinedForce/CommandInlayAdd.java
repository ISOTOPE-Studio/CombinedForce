/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

class CommandInlayAdd implements CommandExecutor {

    private final CombinedForce plugin;

    CommandInlayAdd(CombinedForce plugin) {
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
            if (!isArmor(item.getType())) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("ֻ��Ϊװ��ʹ��")
                        .toString());
                return true;
            }
            if (item.getAmount() != 1) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("������Ʒ��������Ϊ1��")
                        .toString());
                return true;
            }
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            int count = 0;
            for (String line : lore) {
                if (line.contains(plugin.getConfig().getString("add.lore")) ||
                        line.contains(": +")) {
                    count++;
                }
            }
            if (count >= plugin.getConfig().getInt("add.limit")) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����Ʒ�Ŀ���������������").append(plugin.getConfig().getInt("add.limit"))
                        .toString());
                return true;
            }
            lore.add(plugin.getConfig().getString("add.lore"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            player.sendMessage(
                    (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("��ϲ��,�ڻ�ղۿ����ɹ���").toString());
            return true;
        }
        return false;
    }

    private boolean isArmor(Material type) {
        return type == Material.DIAMOND_HELMET || type == Material.GOLD_HELMET || type == Material.CHAINMAIL_HELMET || type == Material.IRON_HELMET || type == Material.LEATHER_HELMET
                || type == Material.DIAMOND_CHESTPLATE || type == Material.GOLD_CHESTPLATE || type == Material.CHAINMAIL_CHESTPLATE || type == Material.IRON_CHESTPLATE || type == Material.LEATHER_CHESTPLATE
                || type == Material.DIAMOND_LEGGINGS || type == Material.GOLD_LEGGINGS || type == Material.CHAINMAIL_LEGGINGS || type == Material.IRON_LEGGINGS || type == Material.LEATHER_LEGGINGS
                || type == Material.DIAMOND_BOOTS || type == Material.GOLD_BOOTS || type == Material.CHAINMAIL_BOOTS || type == Material.IRON_BOOTS || type == Material.LEATHER_BOOTS;
    }
}
