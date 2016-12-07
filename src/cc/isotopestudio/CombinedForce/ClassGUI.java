/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

class ClassGUI implements Listener {

    // From: https://bukkit.org/threads/icon-menu.108342

    private final String name;
    private final int size;
    private final int lorePos;
    private Plugin plugin;
    private String[] optionNames;
    private ItemStack[] optionIcons;
    private boolean ifFinished;
    private ItemStack extra;

    ClassGUI(String name, int lorePos, Plugin plugin) {
        this.name = "Œ‰ªÍ»€¡∂";
        this.size = 27;
        this.lorePos = lorePos;
        this.plugin = plugin;
        this.optionNames = new String[27];
        this.optionIcons = new ItemStack[27];
        ifFinished = false;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        ItemStack yellowGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemStack orangeGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for (int i = 0; i < 27; i++) {
            if (i % 9 == 0 || i % 9 == 8)
                setOption(i, greyGlass, String.valueOf(ChatColor.AQUA) + "Œ‰ªÍ»€¡∂",
                        String.valueOf(ChatColor.GOLD) + ChatColor.ITALIC + "«Î‘⁄÷––ƒ¥¶∑≈÷√»⁄ªÍ");
            if (i % 9 == 1 || i % 9 == 7)
                setOption(i, blueGlass, String.valueOf(ChatColor.AQUA) + "Œ‰ªÍ»€¡∂",
                        String.valueOf(ChatColor.GOLD) + ChatColor.ITALIC + "«Î‘⁄÷––ƒ¥¶∑≈÷√»⁄ªÍ");

            if (i % 9 == 2 || i % 9 == 6)
                setOption(i, yellowGlass, String.valueOf(ChatColor.AQUA) + "Œ‰ªÍ»€¡∂",
                        String.valueOf(ChatColor.GOLD) + ChatColor.ITALIC + "«Î‘⁄÷––ƒ¥¶∑≈÷√»⁄ªÍ");

            if (i % 9 == 3 || i % 9 == 5)
                setOption(i, orangeGlass, String.valueOf(ChatColor.AQUA) + "Œ‰ªÍ»€¡∂",
                        String.valueOf(ChatColor.GOLD) + ChatColor.ITALIC + "«Î‘⁄÷––ƒ¥¶∑≈÷√»⁄ªÍ");
            if (i == 4 || i == 22)
                setOption(i, orangeGlass, String.valueOf(ChatColor.AQUA) + "Œ‰ªÍ»€¡∂",
                        String.valueOf(ChatColor.GOLD) + ChatColor.ITALIC + "«Î‘⁄÷––ƒ¥¶∑≈÷√»⁄ªÍ");

        }
    }

    private void setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
    }

    void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }

    private void Destory() {
        HandlerList.unregisterAll(this);
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            int size = event.getRawSlot();
            int pos = event.getSlot();
            try {
                if (!(event.getCurrentItem().getType().equals(Material.EMERALD)
                        && event.getCursor().getType().equals(Material.AIR)
                        || event.getCursor().getType().equals(Material.EMERALD)
                        && event.getCurrentItem().getType().equals(Material.AIR))) {
                    event.setCancelled(true);
                    return;
                }
            } catch (Exception e) {
                event.setCancelled(true);
                return;
            }

            if (size == 13 && pos == 13) {
                Plugin plugin = this.plugin;
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        ItemStack gem = event.getInventory().getItem(13);
                        if (gem.getAmount() != 1) {
                            extra = gem.clone();
                        }
                        Player player = (Player) event.getWhoClicked();
                        int index = 0, pos = -1;
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
                            return;
                        }
                        if (pos == -1) {
                            return;
                        }
                        ifFinished = true;
                        ItemStack item = player.getItemInHand();
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = item.getItemMeta().getLore();
                        lore.set(lorePos, plugin.getConfig().getString("loreprefix","") + loreString);
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        player.closeInventory();
                        player.sendMessage((new StringBuilder(CombinedForce.prefix)).append(ChatColor.AQUA)
                                .append("Œ‰ªÍ≥…π¶»⁄∫œ£°").toString());
                        Destory();
                    }
                }, 5);

            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            if (event.getInventory().getItem(13) != null) {
                ItemStack item = event.getInventory().getItem(13).clone();
                if (ifFinished) {
                    item.setAmount(item.getAmount() - 1);
                }
                event.getPlayer().getWorld().dropItem(event.getPlayer().getEyeLocation(), item);
            }
            Destory();
        }
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
}