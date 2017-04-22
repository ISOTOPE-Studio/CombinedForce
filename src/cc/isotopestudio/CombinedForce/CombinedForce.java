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

    public static final String prefix = (new StringBuilder()).append(ChatColor.GREEN).append("[�ڻ�ϵͳ]")
            .append(ChatColor.RESET).toString();

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new UnbreakableListener(), this);
        getLogger().info("���������ļ���");
        createFile();

        this.getCommand("inlay").setExecutor(new CommandInlay());
        this.getCommand("inlayadd").setExecutor(new CommandInlayAdd());
        this.getCommand("inlaygem").setExecutor(new CommandInlayGem());
        this.getCommand("outgem").setExecutor(new CommandOutGem());

        getLogger().info("CombinedForce " + getDescription().getVersion() + "�ɹ�����!");
        getLogger().info("CombinedForce ��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info("CombinedForce �ɹ�ж��!");
    }

    private void createFile() {
        File file;
        file = new File(getDataFolder(), "config" + ".yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
    }

}
