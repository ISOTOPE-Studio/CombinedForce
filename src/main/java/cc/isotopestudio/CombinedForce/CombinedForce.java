package cc.isotopestudio.CombinedForce;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CombinedForce extends JavaPlugin {

	public static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[")
			.append(ChatColor.ITALIC).append(ChatColor.BOLD).append("��Ƕ").append(ChatColor.RESET)
			.append(ChatColor.GREEN).append("]").append(ChatColor.RESET).toString();

	@Override
	public void onEnable() {

		this.getCommand("inlay").setExecutor(new CommandInlay(this));

		getLogger().info("CombinedForce �ɹ�����!");
		getLogger().info("CombinedForce ��ISOTOPE Studio����!");
		getLogger().info("http://isotopestudio.cc");
	}

	@Override
	public void onDisable() {
		getLogger().info("CombinedForce �ɹ�ж��!");
	}
}
