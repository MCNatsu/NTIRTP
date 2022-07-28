package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TP implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();
        Player p = (Player) sender;

        if(plugin.coolDowns.containsKey(p.getUniqueId())){

            Long TimeLeft = plugin.coolDowns.get(p.getUniqueId()) - System.currentTimeMillis();
            if(TimeLeft > 0){
                p.sendMessage(NTIRTP.format + ChatColor.RED + "傳送冷卻尚未結束!"
                        + ChatColor.GRAY + "(剩餘秒數:" + ChatColor.RED + (double) TimeLeft/1000 + ChatColor.GRAY + "秒)");
                return false;
            }

        }



        Player target = Bukkit.getPlayerExact(args[0]);

        if(args.length != 1){
            p.sendMessage(NTIRTP.format + ChatColor.RED + "請輸入你要發送請求的玩家!");
            return false;
        }

        if(target == null){
            p.sendMessage(NTIRTP.format + ChatColor.RED + "該玩家不存在或不在線上!");
            return false;
        }

        if(NTIRTP.data.existTP(p)){
            p.sendMessage(NTIRTP.format + ChatColor.RED + "你有發送給" + ChatColor.LIGHT_PURPLE + NTIRTP.data.getTarget(p).getDisplayName() + "的請求!請稍後在試!");
            return false;
        }

        NTIRTP.data.request(p,target,"tpa");

        p.sendMessage(NTIRTP.format +ChatColor.GREEN + "成功傳送TP請求至" + ChatColor.AQUA + target.getDisplayName());
        p.sendMessage(NTIRTP.format + ChatColor.LIGHT_PURPLE + "可輸入/cancel來取消請求");

        TextComponent agree = new TextComponent(NTIRTP.format + ChatColor.AQUA + "玩家" + ChatColor.LIGHT_PURPLE + p.getDisplayName() + ChatColor.AQUA + "請求TP到你這裡" + ChatColor.GREEN + " [同意]" );
        agree.setBold(true);
        agree.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/agree"));
        agree.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("可以點擊此處同意請求").color(ChatColor.GREEN).italic(true).create()));

        TextComponent deny = new TextComponent(ChatColor.RED + " [拒絕]" );
        deny.setBold(true);
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deny"));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("可以點擊此處拒絕請求").color(ChatColor.RED).italic(true).create()));


        target.spigot().sendMessage(agree,deny);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(NTIRTP.data.existTP(p)) {
                    target.sendMessage(NTIRTP.format + ChatColor.RED + "玩家" + ChatColor.YELLOW + p.getDisplayName() + ChatColor.RED + "傳送給你的tp請求已失效");
                    p.sendMessage(NTIRTP.format + ChatColor.RED + "你傳送給玩家" + ChatColor.YELLOW + target.getDisplayName() + ChatColor.RED + "的tp請求已失效");
                    NTIRTP.data.cancelRequest(p);
                }
            }
        }.runTaskLater(plugin, 600);

        return true;


    }
}
