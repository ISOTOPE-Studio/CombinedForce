/*
 * Copyright (c) 2017. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce.command;

import cc.isotopestudio.CombinedForce.CombinedForce;
import org.bukkit.Bukkit;
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

public class CommandInlayGem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("inlaygem")) {
            Player player;
            if (!(sender instanceof Player)) {
                if (args.length < 1)
                    return false;
                player = Bukkit.getPlayer(args[0]);
                if (player == null) return false;
            } else
                player = (Player) sender;
            if (!player.hasPermission("CombinedForce.gem")) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("你没有权限").toString());
                return true;
            }
            ItemStack item = new ItemStack(Material.COAL);
            List<String> lore = new ArrayList<>();
            lore.add(plugin.getConfig().getString("unbreakable.lore"));
            ItemMeta meta = item.getItemMeta();
            meta.setLore(lore);
            meta.setDisplayName(plugin.getConfig().getString("unbreakable.name"));
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
            player.sendMessage(
                    (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("获得一个无限耐久宝石！").toString());
            return true;
        }
        return false;
    }

}
