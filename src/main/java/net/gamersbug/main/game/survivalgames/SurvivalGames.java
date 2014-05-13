package net.gamersbug.main.game.survivalgames;

import java.util.logging.Level;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.game.survivalgames.event.SurvivalGamesEvent;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class SurvivalGames {
    
    private static boolean hasGameStarted, hasGameEnded;
    
    private static SurvivalGames gameInstance;
    
    private final SurvivalGamesEvent survivalGamesEvent = new SurvivalGamesEvent();
            
    private static Integer startPreGameTaskID, startEndGameTaskID,
                           timerSeconds, timerSecondsTotal,
                           playersAlive, playersDead;
    
    public void enableGame() {
        
        gameInstance = this;
        
        Bukkit.getPluginManager().registerEvents(survivalGamesEvent, GamersBug.getPlugin());
        
        setGameStartedState(false);
        setGameEndedState(false);
        
        startPreGameTimer();
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] SurvivalGames has been enabled.");
        
    }
    
    public void disableGame() {
        
        HandlerList.unregisterAll(survivalGamesEvent);
        
        Bukkit.getLogger().log(Level.INFO, "[GamersBug] SurvivalGames has been disabled.");
        
    }
    
    public static SurvivalGames getInstance() {
        
        return gameInstance;
        
    }
    
    public static boolean hasGameStarted() {
        
        return hasGameStarted;
        
    }

    public static void setGameStartedState(boolean gameStarted) {

        hasGameStarted = gameStarted;

    }

    public static boolean hasGameEnded() {

        return hasGameEnded;

    }

    public static void setGameEndedState(boolean gameEnded) {

        hasGameEnded = gameEnded;

    }

    public static void setPlayerDeath() {

        setPlayersAlive(playersAlive - 1);

        setPlayersDead(playersDead + 1);

    }

    public static void setPlayersAlive(Integer playersAliveAmount) {

        playersAlive = playersAliveAmount;

        ScoreboardUtil.setScore("&aAlive", playersAliveAmount);

    }

    public static void setPlayersDead(Integer playersDeadAmount) {

        playersDead = playersDeadAmount;

        ScoreboardUtil.setScore("&cDead", playersDead);

    }
    
    public void checkForWinner() {
        
        if(hasGameStarted() && !hasGameEnded()) {
            
            if(TeamUtil.getPlayersPlaying().size() == 1) {

                Player player = TeamUtil.getPlayersPlaying().get(0);

                MessageManager.broadcastActionMessage("Winner", player.getDisplayName() + "&f has won the game!", ChatColor.GREEN, ChatColor.WHITE);

                startEndGameTimer();

            }else if(TeamUtil.getPlayersPlaying().size() < 1) {

                MessageManager.broadcastActionMessage("Winner", "Well, that's weird! It appears that no one has won the game. o_0", ChatColor.GREEN, ChatColor.RED);

                startEndGameTimer();

            }
        
        }
        
    }
    
    public Runnable startPreGameTimer() {

        timerSecondsTotal = 15;
        
        timerSeconds = timerSecondsTotal;

        startPreGameTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GamersBug.getPlugin(), new Runnable() {

            @Override
            public void run() {

                if(timerSeconds > 0) {

                    BarUtil.setMessageAll("The game will start in &b" + timerSeconds + "&f seconds(s)!", MiscUtil.getTimeRemainingPercentage(timerSeconds, timerSecondsTotal));

                    if(timerSeconds <= 5) {
                        
                        for(Player player : Bukkit.getOnlinePlayers()) {

                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 0);

                        }
                    
                    }

                }else{

                    BarUtil.removeBarAll();

                    Bukkit.getServer().getScheduler().cancelTask(startPreGameTaskID);
                    
                    if(TeamUtil.getPlayersPlaying().size() > 1) {
                    
                        TeamUtil.setTeamsLocked();

                        setGameStartedState(true);

                        MessageManager.broadcastGameMessage("May the odds be ever in your favor.");

                        ScoreboardUtil.createScoreboard("&4» &6Survival Games &4«");

                        setPlayersAlive(TeamUtil.getTotalPlayersPlaying());

                        setPlayersDead(0);

                        ScoreboardUtil.setScoreboardForPlayers(Bukkit.getOnlinePlayers());
                    
                    }else{

                        MessageManager.broadcastGameMessage("&cSorry, but there must be at least two players to start a Survival Games match. You will now be sent back to the pre-game lobby.");
                        
                        startEndGameTimer();
                        
                    }

                }

                timerSeconds--;

            }
            
        }, 20L, 20L);

        return null;

    }

    public Runnable startEndGameTimer() {

        timerSecondsTotal = 15;

        timerSeconds = timerSecondsTotal;

        setGameEndedState(true);

        startEndGameTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GamersBug.getPlugin(), new Runnable() {

            @Override
            public void run() {

                if(timerSeconds > 0) {

                    BarUtil.setMessageAll("Cycling to the pre-game lobby in &b" + timerSeconds + "&f seconds(s)!", MiscUtil.getTimeRemainingPercentage(timerSeconds, timerSecondsTotal));

                    if(timerSeconds <= 5) {

                        for(Player player : Bukkit.getOnlinePlayers()) {

                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 0);

                        }

                    }

                }else{

                    ScoreboardUtil.removeScoreboardForPlayers(Bukkit.getOnlinePlayers());

                    BarUtil.removeBarAll();

                    Bukkit.getServer().getScheduler().cancelTask(startEndGameTaskID);

                    WorldUtil.switchToNewWorld();

                }

                timerSeconds--;

            }

        }, 20L, 20L);

        return null;

    }
    
}