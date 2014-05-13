package net.gamersbug.main.util;

import net.gamersbug.main.model.ServerModel;

public class ServerUtil {

    public static void initServer() {

        if(!ServerModel.serverExists(PluginUtil.getServerCategory(), PluginUtil.getServerNumber())) {

            ServerModel.createServer(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), "Unknown", "Unknown", 1, 0, 0);

        }else{

            ServerModel.updateAll(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), "Unknown", "Unknown", 1, 0, 0);

        }

    }

    public static void shutdownServer() {

        ServerModel.updateAll(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), "Unknown", "Unknown", 0, 0, 0);

    }

    public static void updatePlayersOnlineTotal(Integer playersOnlineInt) {

        ServerModel.updatePlayers(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), playersOnlineInt);

    }

    public static void updateMapAndGame(String currentMap, String currentGame) {

        ServerModel.updateMapAndGame(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), currentMap, currentGame);

    }

    public static void updatePlayingState(Integer playingState) {

        ServerModel.updatePlayingState(PluginUtil.getServerCategory(), PluginUtil.getServerNumber(), playingState);

    }

}
