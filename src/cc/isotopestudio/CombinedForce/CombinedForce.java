/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.CombinedForce;

import cc.isotopestudio.CombinedForce.command.CommandInlay;
import cc.isotopestudio.CombinedForce.command.CommandInlayAdd;
import cc.isotopestudio.CombinedForce.command.CommandInlayGem;
import cc.isotopestudio.CombinedForce.command.CommandOutGem;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class CombinedForce extends JavaPlugin {

    public static CombinedForce plugin;

    public static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[融魂系统]")
            .append(ChatColor.RESET).toString();

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new UnbreakableListener(), this);
        getLogger().info("加载配置文件中");
        createFile();

        this.getCommand("inlay").setExecutor(new CommandInlay());
        this.getCommand("inlayadd").setExecutor(new CommandInlayAdd());
        this.getCommand("inlaygem").setExecutor(new CommandInlayGem());
        this.getCommand("outgem").setExecutor(new CommandOutGem());

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
