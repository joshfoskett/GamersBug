package net.gamersbug.main.util;

import me.confuser.barapi.BarAPI;
import net.gamersbug.main.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BarUtil {

    public static void removeBar(Player player) {

        if(BarAPI.hasBar(player)) {

            BarAPI.removeBar(player);

        }

    }

    public static void removeBarAll() {

        for(Player player : Bukkit.getOnlinePlayers()) {

            removeBar(player);

        }

    }

    public static void setMessage(Player player, String message, Float percentage) {

        BarAPI.setMessage(player, MessageManager.addChatColorToString(message), percentage);

    }

    public static void setMessageAll(String message, Float percentage) {

        for(Player player : Bukkit.getOnlinePlayers()) {

            setMessage(player, message, percentage);

        }

    }

}
