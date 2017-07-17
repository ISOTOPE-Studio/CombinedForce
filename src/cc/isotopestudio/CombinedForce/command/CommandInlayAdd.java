/*
 * Copyright (c) 2017. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce.command;

import cc.isotopestudio.CombinedForce.CombinedForce;
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

import static cc.isotopestudio.CombinedForce.CombinedForce.plugin;
import static org.bukkit.Material.*;

public class CommandInlayAdd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("inlayadd")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("CombinedForce.add")) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("你没有权限").toString());
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!isArmor(item.getType())) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("只能为装备使用")
                        .toString());
                return true;
            }
            if (item.getAmount() != 1) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("手中物品数量必须为1个")
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
                        CommandInlay.isGemLore(line)) {
                    count++;
                }
            }
            if (count >= plugin.getConfig().getInt("add.limit")) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品的开孔数量超过限制").append(plugin.getConfig().getInt("add.limit"))
                        .toString());
                return true;
            }
            lore.add(plugin.getConfig().getString("add.lore"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            player.sendMessage(
                    (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("恭喜你,融魂空槽开启成功！").toString());
            return true;
        }
        return false;
    }

    private boolean isArmor(Material type) {
        return type == DIAMOND_HELMET || type == GOLD_HELMET || type == CHAINMAIL_HELMET || type == IRON_HELMET || type == LEATHER_HELMET
                || type == DIAMOND_CHESTPLATE || type == GOLD_CHESTPLATE || type == CHAINMAIL_CHESTPLATE || type == IRON_CHESTPLATE || type == LEATHER_CHESTPLATE
                || type == DIAMOND_LEGGINGS || type == GOLD_LEGGINGS || type == CHAINMAIL_LEGGINGS || type == IRON_LEGGINGS || type == LEATHER_LEGGINGS
                || type == DIAMOND_BOOTS || type == GOLD_BOOTS || type == CHAINMAIL_BOOTS || type == IRON_BOOTS || type == LEATHER_BOOTS
                /*|| type == DIAMOND_SWORD || type == GOLD_SWORD || type == IRON_SWORD || type == WOOD_SWORD*/;
    }
}
