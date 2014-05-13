package net.gamersbug.main.config;

import java.io.File;
import net.gamersbug.main.GamersBug;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PluginConfig {   
    
    private static FileConfiguration pluginConfig = null;
    
    public static FileConfiguration getConfig() {
        
        return pluginConfig;
        
    }
    
    public static void initPluginConfig() {
        
        File pluginConfigFile = new File(GamersBug.getPlugin().getDataFolder(), "config.yml");
        
        pluginConfig = YamlConfiguration.loadConfiguration(pluginConfigFile);
        
    }
    
}
