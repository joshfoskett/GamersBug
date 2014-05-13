package net.gamersbug.main.game.hub;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.game.hub.object.ServerCategoryObject;
import net.gamersbug.main.object.ServerObject;
import net.gamersbug.main.util.IconMenuUtil;
import net.gamersbug.main.util.ServersUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ServerSelector {

    public static void openServerSelector(String serverCategoryName, Player player) {

        final String serverCategory = getServerCategory(serverCategoryName);

        if(serverCategory == null) {

            MessageManager.playerActionMessage("Error", "Sorry, that category doesn't exist.", ChatColor.RED, ChatColor.WHITE, player);

        }else {

            List<ServerObject> serversByCategory = ServersUtil.getServersByCategory(serverCategory);

            if(serversByCategory.size() < 1) {

                MessageManager.playerActionMessage("Error", "Sorry, that category doesn't have any servers loaded.", ChatColor.RED, ChatColor.WHITE, player);

            }else {

                Double totalRows = Math.ceil(serversByCategory.size() / 9.0) * 9;

                IconMenuUtil iconMenu = new IconMenuUtil(serverCategoryName, totalRows.intValue(), new IconMenuUtil.OptionClickEventHandler() {

                    @Override
                    public void onOptionClick(IconMenuUtil.OptionClickEvent event) {

                        String serverString = ChatColor.stripColor(event.getName()).toLowerCase();

                        String serverId = ServersUtil.makeServerName(serverCategory, getServerNumber(serverString));

                        try {

                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            DataOutputStream out = new DataOutputStream(b);

                            out.writeUTF("Connect");
                            out.writeUTF(serverId);

                            event.getPlayer().sendPluginMessage(GamersBug.getPlugin(), "BungeeCord", b.toByteArray());

                        }catch(IOException e) {

                            e.printStackTrace();

                        }

                        event.setWillClose(true);

                        event.setWillDestroy(true);

                    }

                }, GamersBug.getPlugin());

                Integer slotNumber = 0;

                for(ServerObject serverObject : serversByCategory) {

                    String iconName       = MessageManager.addChatColorToString("&rServer " + serverObject.getNumber());

                    String currentGame    = MessageManager.addChatColorToString("&r&eGame: &f" + serverObject.getGame());
                    String currentMap     = MessageManager.addChatColorToString("&r&eMap: &f" + serverObject.getMap());
                    String currentPlayers = MessageManager.addChatColorToString("&r&ePlayers: &f" + serverObject.getPlayers());

                    String currentStatus;
                    Material itemMaterial;

                    Integer playersOnline = serverObject.getPlayers();

                    if(!serverObject.getOnlineState()) {

                        currentStatus  = MessageManager.addChatColorToString("&r&eStatus: &cOFFLINE");

                        itemMaterial = Material.REDSTONE_BLOCK;

                    }else{

                        currentStatus  = MessageManager.addChatColorToString("&r&eStatus: " + (serverObject.isPlaying() ? "&6PLAYING" : "&aWAITING"));

                        itemMaterial = (serverObject.isPlaying() ? Material.GOLD_BLOCK : Material.EMERALD_BLOCK);

                    }

                    iconMenu.setOption(slotNumber, new ItemStack(itemMaterial, (playersOnline <= 1 ? 1 : playersOnline)), iconName, "", currentGame, currentMap, currentPlayers, "", currentStatus);

                    slotNumber++;

                }

                iconMenu.open(player);



            }

        }

    }

    private static String getServerCategory(String serverCategoryName) {

        for(ServerCategoryObject serverCategoryObject : Hub.getServerCategories()) {

            if(serverCategoryObject.getName() == serverCategoryName) {

                return serverCategoryObject.getCategory();

            }

        }

        return null;

    }

    private static Integer getServerNumber(String serverString) {

        String splitString[] = serverString.split(" ");

        return Integer.parseInt(splitString[1]);

    }

}
