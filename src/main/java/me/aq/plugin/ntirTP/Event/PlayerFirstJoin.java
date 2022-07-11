package me.aq.plugin.ntirTP.Event;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerFirstJoin implements Listener {

    private NTIRTP plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        plugin = NTIRTP.getPlugin();

        if(e.getPlayer().hasPlayedBefore()){return;}

        Player p = e.getPlayer();
        Location location = NTIRTP.data.getSpawn(p.getServer().getMotd());
        p.teleport(location);
        p.setBedSpawnLocation(NTIRTP.data.getSpawn(p.getServer().getMotd()));

    }
}
