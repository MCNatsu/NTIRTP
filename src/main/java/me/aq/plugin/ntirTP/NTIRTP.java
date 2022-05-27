package me.aq.plugin.ntirTP;

import me.aq.plugin.ntirTP.Command.Admin.setSpawn;
import me.aq.plugin.ntirTP.Command.Admin.tpo;
import me.aq.plugin.ntirTP.Command.Home.delhome;
import me.aq.plugin.ntirTP.Command.Home.home;
import me.aq.plugin.ntirTP.Command.Home.homelist;
import me.aq.plugin.ntirTP.Command.Home.sethome;
import me.aq.plugin.ntirTP.Command.TAB.homeTAB;
import me.aq.plugin.ntirTP.Command.TP.*;
import me.aq.plugin.ntirTP.Event.*;
import me.aq.plugin.ntirTP.SQL.SQLMain;
import me.aq.plugin.ntirTP.SQL.SQLeditor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class NTIRTP extends JavaPlugin {

    public HashMap<UUID,Long> coolDowns = new HashMap<>();
    public HashMap<UUID,Long> lastMove = new HashMap<>();
    public SQLMain SQL;
    public SQLeditor data;
    private static NTIRTP plugin;
    FileConfiguration config = getConfig();
    public String format = ChatColor.GRAY+ "[" + ChatColor.GOLD + "傳送" + ChatColor.GRAY + "]" + ChatColor.RESET + "";


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
        //tp
        if(getConfig().getBoolean("EnableTP")) {
            getCommand("tpa").setExecutor(new TP());
            getCommand("tpahere").setExecutor(new tpahere());
            getCommand("agree").setExecutor(new Agree());
            getCommand("deny").setExecutor(new Deny());
            getCommand("Cancel").setExecutor(new Cancel());
            getCommand("back").setExecutor(new back());
            getCommand("tpo").setExecutor(new tpo());
        }

        //spawn
        getCommand("spawn").setExecutor(new spawn());
        getCommand("setspawn").setExecutor(new setSpawn());

        //home
        if(getConfig().getBoolean("EnableHome")) {
            getCommand("sethome").setExecutor(new sethome());
            getCommand("home").setExecutor(new home());
            getCommand("delhome").setExecutor(new delhome());
            getCommand("homelist").setExecutor(new homelist());
            getCommand("home").setTabCompleter(new homeTAB());
        }

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
