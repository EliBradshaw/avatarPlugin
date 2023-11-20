package org.ridgerider.me.avatarnations;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AvatarEffectChecker {
    static String lore = "";

    public static void checkMusicDiscs(Player player) {
        PlayerInventory inventory = player.getInventory();
        Boolean allowFlight = false;
        if (hasMusicDisc(inventory, "fire")) {
            applyFireEffects(player);
        }
        if (hasMusicDisc(inventory, "water")) {
            applyWaterEffects(player);
        }
        if (hasMusicDisc(inventory, "air")) {
            applyAirEffects(player);
            allowFlight = true;
        }
        if (hasMusicDisc(inventory, "earth")) {
            applyEarthEffects(player);
        }
        if (hasMusicDisc(inventory, "chi")) {
            applyChiEffects(player);
        }
        if (hasMusicDisc(inventory, "cabbage")) {
            applyCabbageEffects(player);
            allowFlight = true;
        }
        if (allowFlight) {
            player.setAllowFlight(true);
            player.setFlySpeed(0.075f);
        } else if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setAllowFlight(false);
        }
    }

    static boolean hasMusicDisc(PlayerInventory inventory, String discType) {
        Disc disc = DiscData.getDisc(discType);
        Material discMaterial = disc.type;
        String discDisplayName = disc.title;
        ItemStack[] contents = inventory.getContents();

        for (ItemStack item : contents) {
            if (item != null && item.getType() == discMaterial) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null && itemMeta.hasDisplayName() && itemMeta.getDisplayName().equalsIgnoreCase(discDisplayName)) {
                    if (itemMeta.hasLore()) {
                        PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
                        for (Component loreComponent : itemMeta.lore()) {
                            String loreText = serializer.serialize(loreComponent);
                            if (loreText != null && ChatColor.stripColor(loreText).equalsIgnoreCase(lore)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private static void applyFireEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 50, 255, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 50, 0, false, false));
    }

    private static void applyWaterEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 50, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 50, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1, false, false));
        if (player.isInWater()) {
            tickUpExp(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 0, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 0, false, false));
        }
        if (player.isSwimming())
            tickUpExp(player);
    }

    private static void tickUpExp(Player player) {
        float amount = 0.03f;
        if (player.getExp() + amount >= 1.0f) {
            player.setLevel(player.getLevel() + 1);
            player.setExp(0);
        }
        else
            player.setExp(player.getExp() + amount);
    }

    private static void applyAirEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 50, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 50, 3, false, false));
    }

    private static void applyEarthEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 1, false, false));
    }

    private static void applyChiEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 50, 2, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 50, 3, false, false));
    }

    private  static void applyCabbageEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 4, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 50, 0, false, false));
    }
}
