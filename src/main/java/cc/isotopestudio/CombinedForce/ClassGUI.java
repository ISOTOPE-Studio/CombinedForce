package cc.isotopestudio.CombinedForce;

import java.util.Arrays;
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

public class ClassGUI implements Listener {

	// From: https://bukkit.org/threads/icon-menu.108342

	private String name;
	private int size;
	private Plugin plugin;
	private String[] optionNames;
	private ItemStack[] optionIcons;

	public ClassGUI(String name, int size, Plugin plugin) {
		this.name = name;
		this.size = size;
		this.plugin = plugin;
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
		ItemStack yellowGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
		ItemStack orangeGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
		for (int i = 0; i < 27; i++) {
			if (i % 9 == 0 || i % 9 == 8)
				setOption(i, greyGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
						new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
								.toString());
			if (i % 9 == 1 || i % 9 == 7)
				setOption(i, blueGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
						new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
								.toString());

			if (i % 9 == 2 || i % 9 == 6)
				setOption(i, yellowGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
						new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
								.toString());

			if (i % 9 == 3 || i % 9 == 5)
				setOption(i, orangeGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
						new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
								.toString());
			if (i == 4 || i == 22)
				setOption(i, orangeGlass, new StringBuilder().append(ChatColor.AQUA).append("宝石镶嵌").toString(),
						new StringBuilder().append(ChatColor.GOLD).append(ChatColor.ITALIC).append("请在中心处放置宝石")
								.toString());

		}
	}

	public ClassGUI setOption(int position, ItemStack icon, String name, String... info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info);
		return this;
	}

	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons[i] != null) {
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
	}

	public void Destory() {
		HandlerList.unregisterAll(this);
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(final InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {

			System.out.println("\n\n\n");
			System.out.print("Current: " + event.getCurrentItem().getType().toString());
			System.out.print("Cursor: " + event.getCursor().getType().toString());
			int size = event.getRawSlot();
			int pos = event.getSlot();
			System.out.print(event.getAction().toString());
			System.out.print(event.getSlot());
			System.out.print(size);

			if (!(event.getCurrentItem().getType().equals(Material.EMERALD)
					|| event.getCursor().getType().equals(Material.EMERALD))) {
				System.out.print("InCorrect");
				event.setCancelled(true);
				return;
			}

			System.out.println("Correct");

			if (size == 13 && pos == 13) {
				Plugin plugin = this.plugin;
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						ItemStack item = event.getInventory().getItem(13);
						System.out.print(item.getType().toString());
						final Player p = (Player) event.getWhoClicked();
						p.closeInventory();
						p.sendMessage("true");
						Destory();
					}
				}, 5);

			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			try {
				event.getPlayer().getWorld().dropItem(event.getPlayer().getEyeLocation(),
						event.getInventory().getItem(13));
			} catch (Exception e) {
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