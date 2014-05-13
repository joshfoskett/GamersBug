package net.gamersbug.main.util;

import net.gamersbug.main.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardUtil {

    public static Objective scoreboardObjective;

    public static ScoreboardManager scoreboardManager;

    public static void createScoreboard(String objectiveName) {

        scoreboardManager = Bukkit.getScoreboardManager();

        Scoreboard board = scoreboardManager.getNewScoreboard();

        scoreboardObjective = board.registerNewObjective("test", "dummy");

        scoreboardObjective.setDisplayName(MessageManager.addChatColorToString(objectiveName));

        scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public static void setScoreboardForPlayer(Player player) {

        player.setScoreboard(scoreboardObjective.getScoreboard());

    }

    public static void setScoreboardForPlayers(Player players[]) {

        for(Player player : players) {

            player.setScoreboard(scoreboardObjective.getScoreboard());

        }

    }

    public static void removeScoreboardForPlayer(Player player) {

        player.setScoreboard(scoreboardManager.getNewScoreboard());

    }

    public static void removeScoreboardForPlayers(Player players[]) {

        for(Player player : players) {

            player.setScoreboard(scoreboardManager.getNewScoreboard());

        }

    }

    public static void setScore(Player player, Integer scoreInt) {

        Score score = scoreboardObjective.getScore(player);

        score.setScore(scoreInt);

    }

    public static void setScore(String scoreText, Integer scoreInt) {

        scoreText = MessageManager.addChatColorToString(scoreText);

        Score score = scoreboardObjective.getScore(Bukkit.getOfflinePlayer(scoreText));

        score.setScore(scoreInt);

    }

    public static void removeScore(Player player) {

        scoreboardObjective.getScoreboard().resetScores(player);

    }

    public static void removeScore(String scoreText) {

        scoreboardObjective.getScoreboard().resetScores(Bukkit.getOfflinePlayer(scoreText));

    }

}
