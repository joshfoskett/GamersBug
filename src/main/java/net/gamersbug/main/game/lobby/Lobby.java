package net.gamersbug.main.game.lobby;

import java.util.logging.Level;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.ref.Team;
import net.gamersbug.main.game.lobby.event.LobbyEvent;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class Lobby {
    
    public static Lobby lobbyModule;
    
    public static Integer timerSeconds, timerSecondsTotal, startCountdownTaskID;
    
    private final LobbyEvent lobbyEvent = new LobbyEvent();
    
    public void enableGame() {
        
        lobbyModule = this;

        Bukkit.getPluginManager().registerEvents(lobbyEvent, GamersBug.getPlugin());

        startCountdownTaskID = 0;

        Integer totalPlayersOnline = Bukkit.getOnlinePlayers().length;

        if(GameUtil.getMinPlayers() != null && totalPlayersOnline >= GameUtil.getMinPlayers() && !isTimerStarted()) {

            startCountdownTimer();

        } else if (GameUtil.getMinPlayers() != null) {

            Integer totalMorePlayersNeeded = GameUtil.getMinPlayers() - totalPlayersOnline;

            MessageManager.broadcastGameMessage("&cIn order to start the next game, we need &b" + totalMorePlayersNeeded + "&c more players.");

        }

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Lobby has been enabled.");

    }
    
    public void disableGame() {
        
        HandlerList.unregisterAll(lobbyEvent);
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Lobby has been disabled.");
        
    }
    
    public static Lobby getLobby() {
        
        return lobbyModule;
        
    }
    
    public boolean isTimerStarted() {
        
        return (startCountdownTaskID != 0);
        
    }
    
    public Runnable startCountdownTimer() {

        timerSecondsTotal = PluginUtil.getLobbyCountdown();

        timerSeconds = timerSecondsTotal;

        startCountdownTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GamersBug.getPlugin(), new Runnable() {

            @Override
            public void run() {
                
                if(TeamUtil.getTotalPlayers() < GameUtil.getMinPlayers()) {

                    BarUtil.removeBarAll();

                    MessageManager.broadcastGameMessage("&cWe no longer have enough players to begin, please wait until more join.");
                    
                    Bukkit.getServer().getScheduler().cancelTask(startCountdownTaskID);
                    
                    startCountdownTaskID = 0;
                    
                    return;
                    
                }

                if(timerSeconds > 0) {

                    BarUtil.setMessageAll("The game will start in &b" + timerSeconds + "&f seconds(s)!", MiscUtil.getTimeRemainingPercentage(timerSeconds, timerSecondsTotal));

                    if(timerSeconds <= 5) {
                        
                        for(Player player : Bukkit.getOnlinePlayers()) {

                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 0);

                        }
                    
                    }
                    
                    if(timerSeconds == 10) {
                        
                        forcePlayersToTeams();
                        
                    }

                }else{

                    BarUtil.removeBarAll();

                    Bukkit.getServer().getScheduler().cancelTask(startCountdownTaskID);
                    
                    startCountdownTaskID = 0;

                    MessageManager.broadcastGameMessage("&aSwitching to the next game now.");
                    
                    forcePlayersToTeams();
                    
                    WorldUtil.switchToNewWorld();

                }

                timerSeconds--;

            }
            
        }, 20L, 20L);

        return null;

    }
    
    private void forcePlayersToTeams() {
        
        for(Player player : Bukkit.getOnlinePlayers()) {
            
            if(!TeamUtil.isPlayerPlaying(player)) {
                
                String teamPlayingWithLeastPlayers = TeamUtil.getTeamPlayingWithLeastPlayers();

                TeamUtil.addPlayerToTeam(teamPlayingWithLeastPlayers, player, true);
            
            }
            
        }
        
    }
    
}

