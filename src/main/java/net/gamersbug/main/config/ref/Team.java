package net.gamersbug.main.config.ref;

import java.util.ArrayList;
import java.util.List;
import net.gamersbug.main.config.MapConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {
    
    private final String teamKey, teamName, teamColor, teamNameFormatted, teamKitName;
    
    private final List<Player> teamPlayers = new ArrayList<>();
    
    private final Integer maxPlayers, spawnTotal;
    
    private final ChatColor teamChatColor;
    
    private Integer spawnPosition;
    
    public Team(String teamKey) {
        
        this.teamKey           = teamKey;
        this.teamName          = MapConfig.getGameConfig().getString("teams." + teamKey + ".name");
        this.maxPlayers        = MapConfig.getGameConfig().getInt("teams." + teamKey + ".max");
        this.teamColor         = MapConfig.getGameConfig().getString("teams." + teamKey + ".color");
        this.teamChatColor     = ChatColor.valueOf(teamColor.toUpperCase().replace(" ", "_"));
        this.teamNameFormatted = this.teamChatColor + this.teamName + ChatColor.WHITE;
        this.teamKitName       = MapConfig.getGameConfig().getString("teams." + teamKey + ".kit");
        this.spawnTotal        = MapConfig.getGameConfig().getConfigurationSection("spawns." + teamKey + ".locations").getKeys(false).size();
        this.spawnPosition     = 1;
        
    }
    
    public String getKey() {
        
        return this.teamKey;
        
    }
    
    public String getName() {
        
        return this.teamName;
        
    }

    public String getNameFormatted() {

        return this.teamNameFormatted;

    }
    
    public Integer getMaxPlayers() {
        
        return this.maxPlayers;
        
    }
    
    public String getTeamColor() {
        
        return this.teamColor;
        
    }
    
    public ChatColor getChatColor() {
        
        return this.teamChatColor;
        
    }

    public String getKitName() {

        return this.teamKitName;

    }

    public Boolean hasKit() {

        return !this.teamKitName.isEmpty();

    }
    
    public Boolean isPlayerOnTeam(Player player) {
        
        return this.teamPlayers.contains(player);
        
    }
    
    public List<Player> getPlayers() {
        
        return teamPlayers;
        
    }
    
    public Integer getTotalPlayers() {
        
        return this.teamPlayers.size();
        
    }
    
    public void addPlayer(Player player) {
        
        this.teamPlayers.add(player);
        
    }
    
    public void removePlayer(Player player) {
        
        this.teamPlayers.remove(player);
        
    }
    
    public Integer getSpawnTotal() {
        
        return this.spawnTotal;
        
    }
    
    public Integer getSpawnPosition() {
        
        return this.spawnPosition;
        
    }
    
    public void rotateSpawnPosition() {
        
        this.spawnPosition = (this.spawnPosition >= this.spawnTotal ? 1 : this.spawnPosition + 1);
        
    }

}
