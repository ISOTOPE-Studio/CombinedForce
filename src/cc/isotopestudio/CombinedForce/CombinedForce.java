/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CombinedForce extends JavaPlugin {

    static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[融魂系统]")
            .append(ChatColor.RESET).toString();

    @Override
    public void onEnable() {
        getLogger().info("加载配置文件中");
        createFile();

        this.getCommand("inlay").setExecutor(new CommandInlay(this));
        this.getCommand("inlayadd").setExecutor(new CommandInlayAdd(this));

        getLogger().info("CombinedForce " + getDescription().getVersion() + "成功加载!");
        getLogger().info("CombinedForce 由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info("CombinedForce 成功卸载!");
    }

    private void createFile() {

        File file;
        file = new File(getDataFolder(), "config" + ".yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
    }

}
