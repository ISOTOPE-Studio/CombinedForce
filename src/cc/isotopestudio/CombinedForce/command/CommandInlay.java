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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cc.isotopestudio.CombinedForce.CombinedForce.plugin;

public class CommandInlay implements CommandExecutor {

    private final Set<Material> GEMTYPE;

    public CommandInlay() {
        Material[] GEMSLIST = {Material.EMERALD, Material.DIAMOND, Material.INK_SACK,
                Material.GHAST_TEAR, Material.CLAY_BALL, Material.GOLD_NUGGET, Material.COAL};
        GEMTYPE = new HashSet<>(Arrays.asList(GEMSLIST));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static boolean isGemLore(String line) {
        if (line.contains(plugin.getConfig().getString("unbreakable.lore"))) {
            return true;
        }
        int i = line.indexOf("+");
        if (i > -1) {
            String substring = line.substring(i);
            int j = substring.indexOf(" ");
            try {
                if (substring.charAt(j - 1) == '%') {
                    Integer.parseInt(substring.substring(0, j - 1));
                } else {
                    Integer.parseInt(substring.substring(0, j));
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
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

            ItemStack gem = player.getInventory().getItemInMainHand();
            ItemStack weapon = player.getInventory().getItemInOffHand();
            if (gem.getAmount() != 1 || weapon.getAmount() != 1) {
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("������Ʒ��������Ϊ1��")
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
                player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����û�б�ʯ")
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
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����Ʒ�޷���Ƕ��ʯ").toString());
                return true;
            }
            if (weaponLorePos == -1) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("����Ʒ�޷���Ƕ��ʯ").toString());
                return true;
            }

            // Get lore from gem
            List<String> gemlore = gem.getItemMeta().getLore();
            String loreString = null;
            try {
                for (String temp : gemlore) {
                    if (isGemLore(temp)) {
                        loreString = temp;
                        break;
                    }
                }
            } catch (Exception e) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("��Ч�ı�ʯ").toString());
                return true;
            }
            if (loreString == null) {
                player.sendMessage(
                        (new StringBuilder(CombinedForce.prefix)).append(ChatColor.RED).append("��Ч�ı�ʯ").toString());
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
                    .append("���ɹ��ںϣ�").toString());

            return true;
        }
        return false;
    }
}
