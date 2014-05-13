package net.gamersbug.main.game.dodgeball.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class DodgeballEntityEvent implements Listener {
    
    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        
        if(event.getEntity() instanceof Snowball) {
            
            Entity entity = event.getEntity();
            Location location = entity.getLocation();
            World world = entity.getWorld();
            world.dropItemNaturally(location, new ItemStack(Material.SNOW_BALL, 1));
            
        }
        
    }
    
}
