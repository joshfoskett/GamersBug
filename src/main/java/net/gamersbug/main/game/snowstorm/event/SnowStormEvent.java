package net.gamersbug.main.game.snowstorm.event;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.util.GhostUtil;
import net.gamersbug.main.util.TeamUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SnowStormEvent implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {

        Entity damagedEntity = event.getEntity();
        Entity damagerEntity = event.getDamager();

        if(damagedEntity instanceof Player && damagerEntity instanceof Snowball) {

            Player damagedPlayer = (Player) damagedEntity;

            Snowball snowball = (Snowball) damagerEntity;

            if(snowball.getShooter() instanceof Player) {

                Player damagerPlayer = (Player) snowball.getShooter();

                if(TeamUtil.getPlayersTeam(damagedPlayer) != TeamUtil.getPlayersTeam(damagerPlayer)) {

                    if((damagedPlayer.getHealth() - 4) <= 0) {

                        event.setDamage(0);

                        damagedPlayer.setHealth(1);

                        GhostUtil.setGhost(damagedPlayer, true);

                        // Make player a ghost, and what not.

                    }else{

                        event.setDamage(4);

                    }

                    return;

                }

            }

        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerDamage(PlayerToggleSneakEvent event) {

        if(event.isSneaking()) {

            MessageManager.playerActionMessage("Action", "You are currently picking up a snowball.", ChatColor.GREEN, ChatColor.WHITE, event.getPlayer());

        }else{

            MessageManager.playerActionMessage("Action", "You are no longer picking up a snowball.", ChatColor.GREEN, ChatColor.WHITE, event.getPlayer());

        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        GhostUtil.addPlayer(event.getPlayer());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        GhostUtil.removePlayer(event.getPlayer());

    }

}
