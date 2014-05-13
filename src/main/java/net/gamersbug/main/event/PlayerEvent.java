package net.gamersbug.main.event;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.util.PlayerUtil;
import net.gamersbug.main.util.ServerUtil;
import net.gamersbug.main.util.SpawnUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import net.gamersbug.main.util.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

import java.util.logging.Level;

public class PlayerEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {

        ServerUtil.updatePlayersOnlineTotal(Bukkit.getOnlinePlayers().length);

        Player player = event.getPlayer();

        PlayerUtil.resetPlayer(player);

        TeamUtil.addPlayerToTeam("spectators", player, false);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoinMessage(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(null);

        MessageManager.broadcastActionMessage("Join", player.getDisplayName() + " has joined the game.", ChatColor.GRAY, ChatColor.WHITE);

        PlayerUtil.teleportPlayer(player);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuitMessage(PlayerQuitEvent event) {

        event.setQuitMessage(null);

        Player player = event.getPlayer();

        MessageManager.broadcastActionMessage("Quit", player.getDisplayName() + "&7 has left the game.", ChatColor.GRAY, ChatColor.GRAY);

        ServerUtil.updatePlayersOnlineTotal(Bukkit.getOnlinePlayers().length - 1);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        
        Player player = event.getPlayer();
        
        if(PlayerUtil.isPlayerVisibilityEnabled()) {
            
            for(Player otherPlayer : Bukkit.getOnlinePlayers()) {

                otherPlayer.showPlayer(player);
                player.showPlayer(otherPlayer);

            }
            
        }
        
        TeamUtil.removePlayerFromAllTeams(player);
        
    }
    
    @EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
        
        Player player = event.getNamedPlayer();
        
        event.setTag(player.getDisplayName());
        
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        event.setDeathMessage(null);

        MessageManager.broadcastActionMessage("Death", PlayerUtil.createDeathMessage(event), ChatColor.GRAY, ChatColor.WHITE);

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();

        event.setRespawnLocation(SpawnUtil.getSpawnLocation(player));

        PlayerUtil.giveKit(player);

    }
    
}
