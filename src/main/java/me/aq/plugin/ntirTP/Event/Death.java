package me.aq.plugin.ntirTP.Event;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener {

    private NTIRTP plugin;

    @EventHandler
    public void PlayerDie(PlayerDeathEvent e){

        plugin=NTIRTP.getPlugin();
        Player p = e.getEntity();

        NTIRTP.data.setback(p,p.getServer().getMotd(),p.getLocation());
        p.sendMessage(NTIRTP.format + ChatColor.LIGHT_PURPLE + "已紀錄死亡位置!使用" + ChatColor.GOLD + "/back" + ChatColor.LIGHT_PURPLE +"回到死亡位置");

    }

}
