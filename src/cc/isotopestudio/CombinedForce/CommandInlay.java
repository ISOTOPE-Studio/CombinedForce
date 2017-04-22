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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cc.isotopestudio.CombinedForce.CombinedForce.plugin;

class CommandInlay implements CommandExecutor {

    private final Set<Material> GEMTYPE;

    CommandInlay() {
        Material[] GEMSLIST = {Material.EMERALD, Material.DIAMOND, Material.INK_SACK,
                Material.GHAST_TEAR, Material.CLAY_BALL, Material.GOLD_NUGGET, Material.COAL};
        GEMTYPE = new HashSet<>(Arrays.asList(GEMSLIST));
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

            boolean isGemonMainHand;
            if (GEMTYPE.contains(gem.getType())) {
                isGemonMainHand = true;
            } else if (GEMTYPE.contains(weapon.getType())) {
                isGemonMainHand = false;
                gem = weapon;
                weapon = player.getInventory().getItemInMainHand();
            } else {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("手中没有宝石")
                        .toString());
                return true;
            }

            // Search for available lore in weapon
            int index = 0, weaponLorePos = -1;
            List<String> weaponLore = weapon.getItemMeta().getLore();
            try {
                for (String temp : weaponLore) {
                    if (temp.equals(plugin.getConfig().getString("add.lore"))) {
                        weaponLorePos = index;
                        break;
                    }
                    index++;
                }
            } catch (Exception e) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无法镶嵌宝石").toString());
                return true;
            }
            if (weaponLorePos == -1) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无法镶嵌宝石").toString());
                return true;
            }

            // Get lore from gem
            List<String> gemlore = gem.getItemMeta().getLore();
            String loreString = null;
            try {
                for (String temp : gemlore) {
                    if (temp.contains(": +") || temp.contains(plugin.getConfig().getString("unbreakable.lore"))) {
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
            if (loreString == null) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("无效的宝石").toString());
                return true;
            }

            // Add lore to weapon
            ItemMeta weaponMeta = weapon.getItemMeta();
            weaponLore.set(weaponLorePos, plugin.getConfig().getString("loreprefix", "") + loreString);
            weaponMeta.setLore(weaponLore);
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
