package me.aq.plugin.ntirTP.Event;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DefaultOnlineLocation implements Listener {

    private NTIRTP plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        plugin = NTIRTP.getPlugin();
        Player p = e.getPlayer();

        if(plugin.getConfig().getBoolean("DefaultOnlineLocation")) {
            p.teleport(NTIRTP.data.getSpawn(p.getServer().getMotd()));
        }
    }
}
