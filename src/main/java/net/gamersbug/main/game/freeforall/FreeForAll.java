package net.gamersbug.main.game.freeforall;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.game.freeforall.event.FreeForAllEvent;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class FreeForAll {

    private static Map<UUID, Integer> playerKills = new HashMap<>();

    private static boolean hasGameStarted, hasGameEnded;

    private static FreeForAll gameInstance;

    private final FreeForAllEvent freeForAllEvent = new FreeForAllEvent();

    private static Integer startPreGameTaskID, startGameTaskID, startEndGameTaskID,
                           gameTimeLimit, gameKillLimit,
                           timerSeconds, timerSecondsTotal;

    public void enableGame() {

        gameInstance = this;

        Bukkit.getPluginManager().registerEvents(freeForAllEvent, GamersBug.getPlugin());

        setGameStartedState(false);
        setGameEndedState(false);

        initGameConfig();

        startPreGameTimer();

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Free For All has been enabled.");

    }

    public void disableGame() {

        resetPlayerKills();

        HandlerList.unregisterAll(freeForAllEvent);

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Free For All has been disabled.");

    }

    private static void initGameConfig() {

        gameTimeLimit = MapConfig.getCurrentConfig().getInt("game.time_limit");
        gameKillLimit = MapConfig.getCurrentConfig().getInt("game.kill_limit");

    }

    public static Integer getKillLimit() {

        return gameKillLimit;

    }

    public static FreeForAll getInstance() {

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

    public static Map<UUID, Integer> getPlayerKills() {

        return playerKills;

    }

    public static void resetPlayerKills() {

        playerKills.clear();

    }

    public void setWinner(Player player) {

        if(hasGameStarted() && !hasGameEnded()) {

            BarUtil.removeBarAll();

            Bukkit.getServer().getScheduler().cancelTask(startGameTaskID);

            MessageManager.broadcastActionMessage("Winner", player.getDisplayName() + "&f has won the game!", ChatColor.GREEN, ChatColor.WHITE);

            startEndGameTimer();

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

                        ScoreboardUtil.createScoreboard("&4» &6Free For All &4«");

                        for(Player player : TeamUtil.getPlayersPlaying()) {

                            FreeForAll.getInstance().getPlayerKills().put(player.getUniqueId(), 0);

                            ScoreboardUtil.setScore(player, 0);

                        }

                        ScoreboardUtil.setScoreboardForPlayers(Bukkit.getOnlinePlayers());

                        startGameTimer();

                        MessageManager.broadcastActionMessage("Tip", "First to get &b" + gameKillLimit + "&f kills wins!", ChatColor.GREEN, ChatColor.WHITE);
                        MessageManager.broadcastActionMessage("Tip", "The game will end in &b" + gameTimeLimit + "&f minutes!", ChatColor.GREEN, ChatColor.WHITE);

                    }else{

                        MessageManager.broadcastGameMessage("&cSorry, but there must be at least two players to start a Free For All match. You will now be sent back to the pre-game lobby.");

                        startEndGameTimer();

                    }

                    startPreGameTaskID = 0;

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

                    Map.Entry<UUID, Integer> highestPlayer = null;

                    for(Map.Entry<UUID, Integer> entry : getPlayerKills().entrySet()) {

                        if(highestPlayer == null || entry.getValue() > highestPlayer.getValue()) {

                            highestPlayer = entry;

                        }

                    }

                    setWinner(Bukkit.getPlayer(highestPlayer.getKey()));

                    startGameTaskID = 0;

                }

                timerSeconds--;

            }

        }, 20L, 20L);

        return null;

    }

    public Runnable startEndGameTimer() {

        setGameEndedState(true);

        timerSecondsTotal = 15;

        timerSeconds = timerSecondsTotal;

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

                    startEndGameTaskID = 0;

                }

                timerSeconds--;

            }

        }, 20L, 20L);

        return null;

    }

}