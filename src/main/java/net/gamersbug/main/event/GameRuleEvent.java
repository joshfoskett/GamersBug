package net.gamersbug.main.event;

import java.util.logging.Level;

import net.gamersbug.main.game.survivalgames.SurvivalGames;
import net.gamersbug.main.util.GameUtil;
import net.gamersbug.main.util.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameRuleEvent implements Listener {
    
    /* Game Rule: weatherlock */
    
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
            
        if(GameUtil.getGameRules().get("weatherlock")) {

            event.setCancelled(true);
            
        }
        
    }
    
    /* Game Rule: mobspawns */
    
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        
        if(!GameUtil.getGameRules().get("mobspawns") && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
        
            event.setCancelled(true);
            
        }
        
    }
    
    /* Game Rule: falldamage & playerdamage */
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {

            if (!GameUtil.getGameRules().get("playerdamage")) {

                event.setCancelled(true);

                return;

            }

            if (!GameUtil.getGameRules().get("falldamage")) {

                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                    event.setCancelled(true);

                }

            }

        }
        
    }
    
    /* Game Rule: hunger */
    
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        
        if(!GameUtil.getGameRules().get("hunger")) {
        
            event.setCancelled(true);
        
        }
        
    }

    /* Game Rule: heal */

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {

        if(!GameUtil.getGameRules().get("heal")) {

            event.setCancelled(true);

        }

    }
    
    /* Game Rule: friendlyfire */
    
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        
        if(!GameUtil.getGameRules().get("friendlyfire")) {
            
            if(((event.getDamager() instanceof Player) || 
                (event.getDamager() instanceof Arrow) ||
                (event.getDamager() instanceof Egg) || 
                (event.getDamager() instanceof EnderPearl) ||
                (event.getDamager() instanceof Snowball)) && (event.getEntity() instanceof Player)) {

                Player damaged = (Player) event.getEntity();

                Player damager;

                if(event.getDamager() instanceof Arrow) {

                    Arrow arrow = (Arrow) event.getDamager();

                    damager = (Player) arrow.getShooter();

                }else if(event.getDamager() instanceof Egg) {

                    Egg egg = (Egg) event.getDamager();

                    damager = (Player) egg.getShooter();

                }else if(event.getDamager() instanceof EnderPearl) {

                    EnderPearl enderpearl = (EnderPearl) event.getDamager();

                    damager = (Player) enderpearl.getShooter();    

                }else if(event.getDamager() instanceof Snowball) {

                    Snowball snowball = (Snowball) event.getDamager();

                    damager = (Player) snowball.getShooter();  

                }else{

                    damager =  (Player) event.getDamager();

                }
                
                if(TeamUtil.isPlayerPlaying(damager) && TeamUtil.isPlayerPlaying(damaged)) {
                    
                    if(TeamUtil.getPlayersTeamName(damager).equals(TeamUtil.getPlayersTeamName(damaged))) {
                        
                        event.setCancelled(true);
                    
                    }

                }

            }
        
        }
            
    }
    
    /* Game Rule: blockdamage */    
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        
        if(!GameUtil.getGameRules().get("blockdamage")) {
        
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        
        if(!GameUtil.getGameRules().get("blockdamage")) {
        
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onFHangingBreak(HangingBreakEvent event) {
        
        if(!GameUtil.getGameRules().get("blockdamage")) {
            
            event.setCancelled(true);
            
        }
        
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEvent event) {

        if(!GameUtil.getGameRules().get("blockdamage")) {

            if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {

                event.setCancelled(true);

            }

        }

    }
    
    /* Game Rule: firespread */    
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        
        if(!GameUtil.getGameRules().get("firespread")) {
            
            if(event.getCause() != IgniteCause.FLINT_AND_STEEL) {

                event.setCancelled(true);

            }
        
        }
        
    }

    /* Game Rule: dropitems */

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if(!GameUtil.getGameRules().get("dropitems")) {

            event.getDrops().clear();

        }

    }

    /* Game Rule: interact */

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(!GameUtil.getGameRules().get("interact")) {

            event.setCancelled(true);

        }

    }
    
}
