/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

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

class CommandInlay implements CommandExecutor {

    private final CombinedForce plugin;

    CommandInlay(CombinedForce plugin) {
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

            ItemStack gem = player.getInventory().getItemInMainHand();
            ItemStack weapon = player.getInventory().getItemInOffHand();
            if (gem.getAmount() != 1 || weapon.getAmount() != 1) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("手中物品数量必须为1个")
                        .toString());
                return true;
            }

            boolean isGemonMainHand = true;
            if (gem.getType() == Material.EMERALD) {

            } else if (weapon.getType() == Material.EMERALD) {
                isGemonMainHand = false;
                gem = weapon;
                weapon = player.getInventory().getItemInMainHand();
            } else {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("手中没有宝石")
                        .toString());
                return true;
            }

            // Search for available lore in weapon
            int index = 0, pos = -1;
            List<String> weaponLore = weapon.getItemMeta().getLore();
            try {
                for (String temp : weaponLore) {
                    if (temp.equals(plugin.getConfig().getString("add.lore"))) {
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

            // Get lore from gem
            List<String> gemlore = gem.getItemMeta().getLore();
            String loreString = null;
            try {
                for (String temp : gemlore) {
                    if (temp.contains(": +")) {
                        pos = index;
                        loreString = temp;
                        break;
                    }
                    index++;
                }
            } catch (Exception e) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("无效的宝石").toString());
                return true;
            }
            if (pos == -1) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("无效的宝石").toString());
                return true;
            }

            // Add lore to weapon
            ItemMeta weaponMeta = weapon.getItemMeta();
            List<String> gemLore = weaponMeta.getLore();
            gemLore.set(pos, plugin.getConfig().getString("loreprefix", "") + loreString);
            weaponMeta.setLore(gemLore);
            weapon.setItemMeta(weaponMeta);
            if (isGemonMainHand)
                player.getInventory().setItemInMainHand(null);
            else {
                player.getInventory().setItemInOffHand(null);
            }
            player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.AQUA)
                    .append("武魂成功融合！").toString());

            return true;
        }
        return false;
    }
}
