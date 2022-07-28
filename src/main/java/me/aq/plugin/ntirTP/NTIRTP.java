package me.aq.plugin.ntirTP;

import me.aq.plugin.ntirTP.Command.Admin.reloadConfig;
import me.aq.plugin.ntirTP.Command.Admin.setSpawn;
import me.aq.plugin.ntirTP.Command.Admin.tpo;
import me.aq.plugin.ntirTP.Command.Home.delhome;
import me.aq.plugin.ntirTP.Command.Home.home;
import me.aq.plugin.ntirTP.Command.Home.homelist;
import me.aq.plugin.ntirTP.Command.Home.sethome;
import me.aq.plugin.ntirTP.Command.TAB.homeTAB;
import me.aq.plugin.ntirTP.Command.TP.*;
import me.aq.plugin.ntirTP.Data.Utils.RandomTeleportUtils;
import me.aq.plugin.ntirTP.Event.*;
import me.aq.plugin.ntirTP.Data.SQL.SQLMain;
import me.aq.plugin.ntirTP.Data.SQL.SQLeditor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public final class NTIRTP extends JavaPlugin {

    public HashMap<UUID,Long> coolDowns = new HashMap<>();
    public static SQLMain SQL;
    public static SQLeditor data;
    private static NTIRTP plugin;
    public FileConfiguration config = getConfig();
    public RandomTeleportUtils rtpChecks = new RandomTeleportUtils();

    //StaticConfigDef
    public static String format;
    public static double tpCoolDown;
    public static double tpWaitTime;
    public static double spawnWaitTime;
    public static double HomeWaitTime;

    public static NTIRTP getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        //StaticClassPatch
        plugin = this;
        SQL = new SQLMain();
        data = new SQLeditor();

        config.options().copyDefaults(true);
        this.saveConfig();

        //StaticConfigPatch
        format = config.getString("prefix");
        tpCoolDown = config.getDouble("Cooldown");
        tpWaitTime = config.getDouble("TP-WaitTime");
        spawnWaitTime = config.getDouble("Spawn-WaitTime");
        HomeWaitTime = config.getDouble("Home-WaitTime");

        //資料庫
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
            getCommand("rtp").setExecutor(new rtp());
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

        //reloadConfig
        getCommand("reloadtpconfig").setExecutor(new reloadConfig());
        //監聽器
        getServer().getPluginManager().registerEvents(new PlayerFirstJoin(),this);
        getServer().getPluginManager().registerEvents(new Death(),this);
        getServer().getPluginManager().registerEvents(new DefaultOnlineLocation(),this);
        getServer().getPluginManager().registerEvents(new Respawn(),this);

    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

}
