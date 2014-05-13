package net.gamersbug.main;

import net.gamersbug.main.config.MapConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void playerMessage(String message, Player player) {

        player.sendMessage(addChatColorToString(message));

    }

    public static void playerActionMessage(String action, String message, ChatColor actionColor, ChatColor messageColor, Player player) {

        player.sendMessage(addChatColorToString(actionColor + action + " » " + messageColor + message));

    }

    public static void playerGameMessage(String message, Player player) {

        player.sendMessage(addChatColorToString(getGameName() + message));

    }

    public static void broadcastMessage(String message) {

        Bukkit.broadcastMessage(addChatColorToString(message));

    }

    public static void broadcastActionMessage(String action, String message, ChatColor actionColor, ChatColor messageColor) {

        Bukkit.broadcastMessage(addChatColorToString(actionColor + action + " » " + messageColor + message));

    }

    public static void broadcastGameMessage(String message) {

        broadcastActionMessage(getGameName(), message, ChatColor.GOLD, ChatColor.WHITE);

    }

    public static String addChatColorToString(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);

    }

    private static String getGameName() {

        return MapConfig.getCurrentConfig().getString("game.name");

    }

}
