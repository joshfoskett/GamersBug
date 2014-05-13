package net.gamersbug.main.event;

import net.gamersbug.main.util.TeamUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SpectatorEvent implements Listener {
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        
        if(!TeamUtil.isPlayerPlaying(event.getPlayer())) {
        
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        
        if(!TeamUtil.isPlayerPlaying(event.getPlayer())) {
        
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        
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

            if(!TeamUtil.isPlayerPlaying(damager) || !TeamUtil.isPlayerPlaying(damaged)) {

                event.setCancelled(true);

            }

        }
            
    }
    
    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        
        if(event.getEntity() instanceof Player) {
            
            Player player = (Player) event.getEntity();
            
            if(!TeamUtil.isPlayerPlaying(player)) {
            
                event.setCancelled(true);
            
            }
            
        }
        
    }
    
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        
        if(event.getEntity() instanceof Player) {
            
            Player player = (Player) event.getEntity();
            
            if(!TeamUtil.isPlayerPlaying(player)) {
            
                event.setCancelled(true);
            
            }
            
        }
        
    }
    
    @EventHandler
    public void onBlockHangingBreak(HangingBreakByEntityEvent event) {
        
        if(event.getRemover() instanceof Player) {
            
            Player player = (Player) event.getRemover();
            
            if(!TeamUtil.isPlayerPlaying(player)) {
            
                event.setCancelled(true);
            
            }
            
        }
        
    }    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        
        if(event.getWhoClicked() instanceof Player) {
            
            Player player = (Player) event.getWhoClicked();
            
            if(!TeamUtil.isPlayerPlaying(player)) {
            
                event.setCancelled(true);
            
            }
        
        }
        
    }
    
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        
        if(!TeamUtil.isPlayerPlaying(event.getPlayer())) {
         
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        
        if(!TeamUtil.isPlayerPlaying(event.getPlayer())) {
         
            event.setCancelled(true);
        
        }
        
    }    
    
    @EventHandler    
    public void onPlayerInteract(PlayerInteractEvent event) {
            
        if(!TeamUtil.isPlayerPlaying(event.getPlayer())) {

            event.setCancelled(true);

        }
        
    }
    
}
