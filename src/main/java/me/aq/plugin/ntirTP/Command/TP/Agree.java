package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public class Agree implements CommandExecutor {

    private static NTIRTP plugin;
    Map<String,Long> StartMoveCheck = new HashMap<String,Long>();
    private static final String format = NTIRTP.format;
    private static final double tpCoolDown = NTIRTP.tpCoolDown;
    private static final double tpWaitTime = NTIRTP.tpWaitTime;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        double delay = tpWaitTime*20;

        Player target = (Player) sender;

        if(!NTIRTP.data.existTPT(target)){
            target.sendMessage(format + ChatColor.RED + "你沒有待確認的請求!");
            return false;
        }

        Player requester = NTIRTP.data.getRequester(target);

        if(args.length == 0){

            if(NTIRTP.data.getType(requester).equalsIgnoreCase("tpaHere")){
                Location location = requester.getLocation();

                target.sendMessage(format +ChatColor.GREEN + "成功同意請求!");
                requester.sendMessage(format + ChatColor.GREEN + "對方已同意你的請求");
                target.sendMessage(format + ChatColor.GOLD + "正在發動轉移魔法 " + ChatColor.GRAY + "請稍後" +ChatColor.RED + delay/20 + ChatColor.GRAY + "秒...");

                NTIRTP.data.setback(target,target.getServer().getMotd(),target.getLocation());

                new BukkitRunnable(){
                    @Override
                    public void run() {
                        target.teleport(location);
                        target.sendMessage(format + ChatColor.GRAY + "正在傳送...");
                        NTIRTP.data.cancelRequest(requester);
                    }
                }.runTaskLater(plugin, (long) delay);
                return true;
            }

            Location location = target.getLocation();

            target.sendMessage(format +ChatColor.GREEN + "成功同意請求!");
            StartMoveCheck.put(requester.getDisplayName() ,System.currentTimeMillis());
            requester.sendMessage(format + ChatColor.GREEN + "對方已同意你的請求");
            requester.sendMessage(format + ChatColor.GOLD + "正在發動轉移魔法 " + ChatColor.GRAY + "請稍後" +ChatColor.RED + delay/20 + ChatColor.GRAY + "秒...");

            NTIRTP.data.setback(requester,requester.getServer().getMotd(),requester.getLocation());

            new BukkitRunnable(){
                @Override
                public void run() {

                    requester.teleport(location);
                    requester.sendMessage(format + ChatColor.GRAY + "正在傳送...");
                    NTIRTP.data.cancelRequest(requester);
                }
            }.runTaskLater(plugin, (long) delay);
            plugin.coolDowns.put(requester.getUniqueId(),System.currentTimeMillis() + (long) tpCoolDown*1000);
            return true;
        }

        return false;
    }
}
