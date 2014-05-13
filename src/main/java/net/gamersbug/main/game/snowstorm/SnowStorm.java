package net.gamersbug.main.game.snowstorm;

import java.util.logging.Level;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.config.ref.Team;
import net.gamersbug.main.game.snowstorm.event.SnowStormEvent;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class SnowStorm {

    private static boolean hasGameStarted, hasGameEnded;

    private static SnowStorm gameInstance;

    private final SnowStormEvent snowStormEvent = new SnowStormEvent();

    private static Integer startPreGameTaskID, startGameTaskID, startEndGameTaskID,
            timerSeconds, timerSecondsTotal, gameTimeLimit;

    public void enableGame() {

        gameInstance = this;

        Bukkit.getPluginManager().registerEvents(snowStormEvent, GamersBug.getPlugin());

        setGameStartedState(false);
        setGameEndedState(false);

        initGameConfig();

        GhostUtil.enableGhosts();

        GhostUtil.addPlayers(Bukkit.getOnlinePlayers());

        startPreGameTimer();

        // initScoreboard();

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] SnowStorm has been enabled.");

    }

    public void disableGame() {

        HandlerList.unregisterAll(snowStormEvent);

        GhostUtil.disableGhosts();

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] SnowStorm has been disabled.");

    }

    private static void initGameConfig() {

        gameTimeLimit = MapConfig.getCurrentConfig().getInt("game.time_limit");

    }

    public static SnowStorm getInstance() {

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

    public static void initScoreboard() {

        for(Team teamObject : TeamUtil.getTeamsPlaying().values()) {

            ScoreboardUtil.setScore(teamObject.getNameFormatted(), 0);

        }

        ScoreboardUtil.setScoreboardForPlayers(Bukkit.getOnlinePlayers());

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

                        ScoreboardUtil.createScoreboard("&4» &6Snow Storm &4«");

                        ScoreboardUtil.setScoreboardForPlayers(Bukkit.getOnlinePlayers());

                        startGameTimer();

                    }else{

                        MessageManager.broadcastGameMessage("&cSorry, but there must be at least two players to start a Snow Storm match. You will now be sent back to the pre-game lobby.");

                        startEndGameTimer();

                    }

                }

                timerSeconds--;

            }

        }, 20L, 20L);

        return null;

    }

    public Runnable startGameTimer() {

        setGameStartedState(true);

        timerSecondsTotal = gameTimeLimit * 60;

        timerSeconds = timerSecondsTotal;

        startGameTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GamersBug.getPlugin(), new Runnable() {

            @Override
            public void run() {

                if(timerSeconds > 0) {

                    BarUtil.setMessageAll("&bTime Remaining: &f" + MiscUtil.secondsToTime(timerSeconds), MiscUtil.getTimeRemainingPercentage(timerSeconds, timerSecondsTotal));

                    if (timerSeconds < 5 && timerSeconds > 0) {

                        if (timerSeconds <= 5) {

                            for (Player player : Bukkit.getOnlinePlayers()) {

                                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 0);

                            }

                        }

                    }

                }else{

                    Bukkit.getServer().getScheduler().cancelTask(startGameTaskID);

                    // Decide winner.

                    startGameTaskID = 0;

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