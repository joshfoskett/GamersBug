package net.gamersbug.main.game.kitpvp;

import java.util.logging.Level;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.game.kitpvp.event.KitPvPEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class KitPvP {
    
    KitPvPEvent kitPvPEvent = new KitPvPEvent();
    
    public void enableGame() {
        
        Bukkit.getPluginManager().registerEvents(kitPvPEvent, GamersBug.getPlugin());
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] KitPvP has been enabled.");
        
    }
    
    public void disableGame() {
        
        HandlerList.unregisterAll(kitPvPEvent);
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] KitPvP has been disabled.");
        
    }
    
}
