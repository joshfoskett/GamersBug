package net.gamersbug.main.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.config.ref.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamUtil {
    
    private static final Map<String, Team> Teams = new HashMap<>();
    
    public static boolean teamsLocked = false;
    
    public static Team getTeam(String teamKey) {
        
        return Teams.get(teamKey);
        
    }
    
    public static Team getPlayersTeam(Player player) {
        
        return getTeam(getPlayersTeamKey(player));
        
    }
    
    public static boolean areTeamsLocked() {
        
        return teamsLocked;
        
    }
    
    public static void setTeamsLocked() {
        
        teamsLocked = true;
        
    }
    
    public static void setTeamsUnlocked() {
        
        teamsLocked = false;
        
    }
    
    public static void setTeams() {
        
        setTeamsLocked();
        
        Teams.clear();
        
        for(String teamKey : MapConfig.getGameConfig().getConfigurationSection("teams").getKeys(false)) {
            
           Teams.put(teamKey, new Team(teamKey));
            
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            
            addPlayerToTeam("spectators", player, false);
            
        }
        
        setTeamsUnlocked();
        
    }
    
    public static void addPlayerToTeam(String teamKey, Player player, Boolean notifyPlayer) {
        
        removePlayerFromAllTeams(player);
        
        if(teamKey.equals("spectators")) {
            
            PlayerUtil.setPlayerVisibilityToSpectating(player);
            
        }else{
            
            PlayerUtil.setPlayerVisibilityToPlaying(player);
            
        }
        
        getTeam(teamKey).addPlayer(player);
        
        PlayerUtil.refreshDisplayName(player);

        if(notifyPlayer) {

            MessageManager.playerActionMessage("Teams", "You have joined the " + getTeam(teamKey).getNameFormatted() + ".", ChatColor.GRAY, ChatColor.WHITE, player);

        }
        
    }
    
    public static void removePlayerFromAllTeams(Player player) {
        
        for(String teamKey : Teams.keySet()) {
            
            if(getTeam(teamKey).isPlayerOnTeam(player)) {
                
                getTeam(teamKey).removePlayer(player);
                
            }
            
        } 
        
    }
    
    public static boolean isPlayerPlaying(Player player) {
        
        for(String teamKey : Teams.keySet()) {
            
            if(getTeam(teamKey).isPlayerOnTeam(player) && !teamKey.equals("spectators")) {
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    public static String getPlayersTeamKey(Player player) {
        
        for(String teamKey : Teams.keySet()) {
            
            if(getTeam(teamKey).isPlayerOnTeam(player)) {
                
                return teamKey;
                
            }
            
        }
        
        return "Unknown";
        
    }
    
    public static String getPlayersTeamName(Player player) {
        
        for(String teamKey : Teams.keySet()) {
            
            if(getTeam(teamKey).isPlayerOnTeam(player)) {
                
                return getTeam(teamKey).getName();
                
            }
            
        }
        
        return "Unknown";
        
    }
    
    public static Integer getTotalPlayers() {
        
        Integer totalPlayers = 0;
        
        for(String teamKey : Teams.keySet()) {
            
            totalPlayers = totalPlayers + getTeam(teamKey).getTotalPlayers();
            
        }
        
        return totalPlayers;
        
    }
    
    public static Integer getTotalPlayersPlaying() {
        
        Integer totalPlayersPlaying = 0;
        
        for(String teamKey : Teams.keySet()) {
            
            if(!teamKey.equals("spectators")) {
                
                totalPlayersPlaying = totalPlayersPlaying + getTeam(teamKey).getTotalPlayers();
                
            }
            
        }
        
        return totalPlayersPlaying;
        
    }
    
    public static List<Player> getPlayersPlaying() {
        
        List<Player> playersPlaying = new ArrayList<>();
        
        for(String teamKey : Teams.keySet()) {
            
            if(!teamKey.equals("spectators")) {
                
                playersPlaying.addAll(getTeam(teamKey).getPlayers());
                
            }
            
        }
        
        return playersPlaying;
        
    }
    
    public static String getTeamPlayingWithLeastPlayers() {
        
        String lowestTeamKey = "unknown";
        
        Integer lowestPlayers = 10000;
        
        for(String teamKey : Teams.keySet()) {
            
            if(getTeam(teamKey).getTotalPlayers() < lowestPlayers && !teamKey.equals("spectators")) {
                
                lowestTeamKey = teamKey;
                lowestPlayers = getTeam(teamKey).getTotalPlayers();
                
            }
            
        }
        
        return lowestTeamKey;
        
    }

    public static Map<String, Team> getTeams() {

        return Teams;

    }

    public static Map<String, Team> getTeamsPlaying() {

        Map<String, Team> teamsPlaying = Teams;

        teamsPlaying.remove("spectators");

        return teamsPlaying;

    }
    
}