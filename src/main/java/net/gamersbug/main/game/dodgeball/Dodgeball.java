package net.gamersbug.main.game.dodgeball;

import java.util.logging.Level;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.game.dodgeball.event.DodgeballEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class Dodgeball {
    
    DodgeballEntityEvent dodgeballEntityEvent = new DodgeballEntityEvent();
    
    public void enableGame() {
        
        Bukkit.getPluginManager().registerEvents(dodgeballEntityEvent, GamersBug.getPlugin());
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Dodgeball has been enabled.");
        
    }
    
    public void disableGame() {
        
        HandlerList.unregisterAll(dodgeballEntityEvent);
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Dodgeball has been disabled.");
        
    }
    
}

