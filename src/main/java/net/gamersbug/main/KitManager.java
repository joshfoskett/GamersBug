package net.gamersbug.main;

import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.util.EnchantmentUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class KitManager {

    private static Map<String, Map<Integer, ItemStack>> kitMaps = new HashMap<>();

    public static void resetKits() {

        kitMaps.clear();

    }

    public static Map<Integer, ItemStack> getKit(String kitKey) {

        if(!kitMaps.containsKey(kitKey)){

            buildKit(kitKey);

        }

        return kitMaps.get(kitKey);

    }

    private static void buildKit(String kitKey) {

        Map<Integer, ItemStack> kitMap = createKitMap(kitKey);

        String kitParents = MapConfig.getCurrentConfig().getString("kits." + kitKey + ".parents");

        if(!kitParents.equals("")) {

            if(kitParents.contains(",")) {

                String kitParentsArray[] = kitParents.split(",");

                for(String kitParentKey : kitParentsArray) {

                    kitMap.putAll(getKit(kitParentKey));

                }

            }else{

                kitMap.putAll(getKit(kitParents));

            }

        }

        kitMaps.put(kitKey, kitMap);

    }

    private static Map<Integer, ItemStack> createKitMap(String kitKey) {

        Map<Integer, ItemStack> kitMap = new HashMap<>();

        ConfigurationSection itemConfig = MapConfig.getCurrentConfig().getConfigurationSection("kits." + kitKey + ".items");

        Set<String> itemList = itemConfig.getKeys(false);

        for(String itemSlot : itemList) {

            String itemString = itemConfig.getString(itemSlot + ".item");

            ItemStack itemStack = convertStringWithParamsToItemStack(itemString);

            ItemMeta itemMeta = itemStack.getItemMeta();

            if(itemConfig.isSet(itemSlot + ".name")) {

                itemMeta.setDisplayName(MessageManager.addChatColorToString(itemConfig.getString(itemSlot + ".name")));

            }

            if(itemConfig.isSet(itemSlot + ".lore")) {

                itemMeta.setLore(itemConfig.getStringList(itemSlot + ".lore"));

            }

            itemStack.setItemMeta(itemMeta);

            kitMap.put(Integer.parseInt(itemSlot), itemStack);

        }

        return kitMap;

    }

    private static ItemStack convertStringWithParamsToItemStack(String itemString) {

        ItemStack itemStack;

        if(itemString.contains(" ")) {

            String itemArray[] = itemString.split(" ");

            itemStack = convertStringToItemStack(itemArray[0], Integer.parseInt(itemArray[1]));

            for(String itemParam : itemArray) {

                String splitParam[] = itemParam.split(":");

                if(itemParam.startsWith("e:")) { /* Enchanting */

                    itemStack.addEnchantment(EnchantmentUtil.convertMinecraftToBukkit(splitParam[1]), Integer.parseInt(splitParam[2]));

                }else if(itemParam.startsWith("d:")) { /* Durability */

                    itemStack.setDurability(Short.parseShort(splitParam[1]));

                }else if(itemParam.startsWith("c:")) { /* Color */

                    LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
                    itemMeta.setColor(Color.fromRGB(Integer.parseInt(splitParam[1], 16)));
                    itemStack.setItemMeta(itemMeta);

                }

            }

        }else{

            itemStack = convertStringToItemStack(itemString, 1);

        }

        return itemStack;

    }

    private static ItemStack convertStringToItemStack(String itemString, Integer itemAmount) {

        if(itemString.contains(":")) {

            String itemArray[] = itemString.split(":");

            return new ItemStack(Material.getMaterial(itemArray[0].toUpperCase()), itemAmount, Short.parseShort(itemArray[1]));

        }else{

            return new ItemStack(Material.getMaterial(itemString.toUpperCase()), itemAmount);


        }

    }

}
