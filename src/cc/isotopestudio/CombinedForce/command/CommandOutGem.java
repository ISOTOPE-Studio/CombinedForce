/*
 * Copyright (c) 2017. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce.command;

import cc.isotopestudio.CombinedForce.CombinedForce;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cc.isotopestudio.CombinedForce.CombinedForce.plugin;

public class CommandOutGem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("outgem")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("CombinedForce.out")) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("你没有权限").toString());
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无效").toString());
                return true;
            }
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            List<Integer> holes = new ArrayList<>();
            ConfigurationSection config = plugin.getConfig().getConfigurationSection("unload");
            int i = 0;
            for (String line : lore) {
                for (String key : config.getKeys(false)) {
                    if (config.getString(key + ".lore").equals(line)) {
                        holes.add(i);
                        break;
                    }
                }
                i++;
            }
            if (holes.size() < 1) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品无效").toString());
                return true;
            } else if (holes.size() == 1) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("此物品只有一个镶嵌孔").toString());
                return true;
            }
            Collections.shuffle(holes);
            lore.set(holes.remove(0), plugin.getConfig().getString("add.lore"));
            ConsoleCommandSender console = Bukkit.getConsoleSender();
            for (int holeIndex : holes) {
                for (String key : config.getKeys(false)) {
                    if (config.getString(key + ".lore").equals(lore.get(holeIndex))) {
                        lore.set(holeIndex, plugin.getConfig().getString("add.lore"));
                        Bukkit.dispatchCommand(console, config.getString(key + ".cmd").replaceAll("<player>", player.getName()));
                        break;
                    }
                }
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getInventory().setItemInMainHand(item);
            player.sendMessage(
                    (new StringBuilder(CombinedForce.prefix)).append(ChatColor.GREEN).append("成功拆卸").toString());
            return true;
        }
        return false;
    }

}
