package net.gamersbug.main;

import java.io.File;
import java.util.logging.Level;

import net.gamersbug.main.commands.*;
import net.gamersbug.main.util.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import net.gamersbug.main.event.GameRuleEvent;
import net.gamersbug.main.event.PlayerEvent;
import net.gamersbug.main.event.SpectatorEvent;
import net.gamersbug.main.util.PluginUtil;
import net.gamersbug.main.util.WorldUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class GamersBug extends JavaPlugin implements PluginMessageListener {
    
    private static Plugin pluginInstance;
    
    @Override
    public void onEnable() {
        
        pluginInstance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        this.initConfig();

        PluginDescriptionFile pluginInfo = this.getDescription();
        
        PluginUtil.initPlugin();

        ServerUtil.initServer();
        
        WorldUtil.initWorldUtil();
        
        WorldUtil.switchToNewWorld();
        
        this.initCommands();

        this.initEvents();

        getLogger().log(Level.INFO, "{0} ({1}) has successfully been initialized!", new Object[]{pluginInfo.getName(), pluginInfo.getVersion()});
        
    }
    
    @Override
    public void onDisable() {

        ServerUtil.shutdownServer();
        
        PluginDescriptionFile pluginInfo = this.getDescription();
        
        getLogger().log(Level.INFO, "{0} ({1}) has successfully been disabled.", new Object[]{pluginInfo.getName(), pluginInfo.getVersion()});
        
    }

    public static Plugin getPlugin() {

        return pluginInstance;

    }
    
    private void initCommands() {

        this.getCommand("team")      .setExecutor(new TeamCommand());
        this.getCommand("join")      .setExecutor(new JoinCommand());
        this.getCommand("leave")     .setExecutor(new LeaveCommand());
        this.getCommand("map")       .setExecutor(new MapCommand());
        this.getCommand("register")  .setExecutor(new RegisterCommand());
        
    }
    
    private void initEvents() {

        getServer().getPluginManager().registerEvents(new PlayerEvent(),    this);
        getServer().getPluginManager().registerEvents(new GameRuleEvent(),  this);
        getServer().getPluginManager().registerEvents(new SpectatorEvent(), this);
        
    }

    private void initConfig() {

        File configFile = new File(this.getDataFolder(), "config.yml");

        if(!configFile.exists()) {

            this.getConfig().addDefault("server", "server01");
            this.getConfig().addDefault("lobby", "Game");
            this.getConfig().addDefault("lobby_countdown", "30");
            this.getConfig().addDefault("player_limit", "50");
            this.getConfig().addDefault("map_repo", "../../../maps/");
            this.getConfig().addDefault("map_method", "file");
            this.getConfig().addDefault("rotation", "");
            this.getConfig().addDefault("rotation.1", "");
            this.getConfig().addDefault("rotation.1.game", "Hub");
            this.getConfig().addDefault("rotation.1.map", "Tropical");

            this.getConfig().options().copyDefaults(true);
            this.saveConfig();

        }

    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        return;

    }
}
