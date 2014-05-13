package net.gamersbug.main.game.kitpvp.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitPvPEvent implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        
        Player player = event.getPlayer();
        
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
              
            return;
            
        }
            
        if(event.getClickedBlock().getTypeId() != 63 && event.getClickedBlock().getTypeId() != 68) {
            
            return;
            
        }
                
        Sign sign = (Sign) event.getClickedBlock().getState();

        if(sign.getLine(0).equals("[Kit]")) {

            switch(sign.getLine(1)) {
                
                case "Save": {
                    
                    player.sendMessage("You have just saved kit " + sign.getLine(2) + ".");
                    
                    break;
                    
                }
                
                case "Load": {
                    
                    player.sendMessage("You have just loaded kit " + sign.getLine(2) + ".");
                    
                    break;
                    
                }
                
            }

        }
        
    }
    
}
