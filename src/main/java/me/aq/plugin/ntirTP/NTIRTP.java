package me.aq.plugin.ntirTP;

import me.aq.plugin.ntirTP.Command.*;
import me.aq.plugin.ntirTP.Event.*;
import me.aq.plugin.ntirTP.SQL.SQLMain;
import me.aq.plugin.ntirTP.SQL.SQLeditor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class NTIRTP extends JavaPlugin {

    public SQLMain SQL;
    public SQLeditor data;
    private static NTIRTP plugin;
    FileConfiguration config = getConfig();
    public String format = ChatColor.GRAY+ "[" + ChatColor.BLUE + "NTIR傳送" + ChatColor.GRAY + "]" + ChatColor.RESET + "";


    public static NTIRTP getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        plugin = this;

        SQL = new SQLMain();
        data = new SQLeditor();
        data.SQLGetter(this);
        config.options().copyDefaults(true);
        this.saveConfig();

        try {
            SQL.connect();
            data.createTable();
        }catch (ClassNotFoundException | SQLException e){
            Bukkit.getLogger().info("DB not connected");
            Bukkit.getLogger().info("資料庫是該插件的必要功能");
            Bukkit.getLogger().info("DISABLING THE PLUGIN...");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("tpa").setExecutor(new TP());
        getCommand("tpahere").setExecutor(new tpahere());
        getCommand("agree").setExecutor(new Agree());
        getCommand("deny").setExecutor(new Deny());
        getCommand("Cancel").setExecutor(new Cancel());
        getCommand("back").setExecutor(new back());
        getCommand("spawn").setExecutor(new spawn());
        getCommand("setspawn").setExecutor(new setSpawn());
        getCommand("sethome").setExecutor(new sethome());
        getCommand("home").setExecutor(new home());
        getCommand("delhome").setExecutor(new delhome());
        getCommand("tpo").setExecutor(new tpo());
        getServer().getPluginManager().registerEvents(new PlayerFirstJoin(),this);
        getServer().getPluginManager().registerEvents(new Death(),this);
        getServer().getPluginManager().registerEvents(new DefaultOnlineLocation(),this);
        getServer().getPluginManager().registerEvents(new Respawn(),this);

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

}
