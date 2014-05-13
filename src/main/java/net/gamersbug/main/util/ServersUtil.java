package net.gamersbug.main.util;

import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.model.ServersModel;
import net.gamersbug.main.object.ServerObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ServersUtil {

    private static List<ServerObject> serverObjectList;

    public static List<ServerObject> getServers() {

        //if(serverObjectList == null) {

            serverObjectList = ServersModel.getServers();

        //}

        return serverObjectList;

    }

    public static List<ServerObject> getServersByCategory(String category) {

        List<ServerObject> serversByCategoryList = new ArrayList<>();

        for(ServerObject serverObject : getServers()) {

            if(serverObject.getCategory().equals(category)) {

                serversByCategoryList.add(serverObject);

            }

        }

        return serversByCategoryList;

    }

    public static String makeServerName(String serverCategory, Integer serverNumber) {

        return serverCategory + "-" + serverNumber;

    }

}
