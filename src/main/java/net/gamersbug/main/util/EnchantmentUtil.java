package net.gamersbug.main.util;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentUtil {

    public static Enchantment convertMinecraftToBukkit(String enchantment) {

        switch(enchantment.toLowerCase()) {

            case "sharpness":             return Enchantment.DAMAGE_ALL;
            case "baneofarthropods":
            case "bane_of_arthropods":    return Enchantment.DAMAGE_ARTHROPODS;
            case "smite":                 return Enchantment.DAMAGE_UNDEAD;
            case "efficiency":            return Enchantment.DIG_SPEED;
            case "unbreaking":            return Enchantment.DURABILITY;
            case "fire_aspect":           return Enchantment.FIRE_ASPECT;
            case "knockback":             return Enchantment.KNOCKBACK;
            case "fortune":               return Enchantment.LOOT_BONUS_BLOCKS;
            case "looting":               return Enchantment.LOOT_BONUS_MOBS;
            case "respiration":           return Enchantment.OXYGEN;
            case "protection":            return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "blastprotection":
            case "blast_protection":      return Enchantment.PROTECTION_EXPLOSIONS;
            case "featherfalling":
            case "feather_falling":       return Enchantment.PROTECTION_FALL;
            case "fireprotection":
            case "fire_protection":       return Enchantment.PROTECTION_FIRE;
            case "projectileprotection":
            case "projectile_protection": return Enchantment.PROTECTION_PROJECTILE;
            case "silktouch":
            case "silk_touch":            return Enchantment.SILK_TOUCH;
            case "aquaaffinity":
            case "aqua_affinity":         return Enchantment.WATER_WORKER;
            case "thorns":                return Enchantment.THORNS;
            case "flame":                 return Enchantment.ARROW_FIRE;
            case "power":                 return Enchantment.ARROW_DAMAGE;
            case "punch":                 return Enchantment.ARROW_KNOCKBACK;
            case "infinity":              return Enchantment.ARROW_INFINITE;

            default:                      return Enchantment.LUCK;

        }

    }

}
