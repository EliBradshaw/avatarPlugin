package org.ridgerider.me.avatarnations;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;


public class AvatarAbilityHandler implements Listener {
    @EventHandler
    public void onFireBall(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().isEmpty())
            return;
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), "fire"))
            return;
        // Get the player's location and direction
        Vector direction = player.getEyeLocation().getDirection();
        // Create a fireball entity
        Fireball fireball = player.launchProjectile(Fireball.class, direction);
        // Set additional properties if needed (speed, yield, etc.)
        // For example:
        fireball.setVelocity(direction.multiply(0.75)); // Adjust the speed by multiplying the direction vector
        fireball.setYield(0.0F); // Set explosion power (if needed)

        // Create a new direction vector (adjust as needed)
        Vector newDirection = new Vector(0, 0, 0); // Example: changing to look directly along the X-axis

        // Set the player's velocity to change the view direction
        player.setVelocity(newDirection.subtract(direction).multiply(0.4));
    }

    @EventHandler
    public void onBlockPick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().isEmpty())
            return;
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), "earth"))
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (event.getClickedBlock().getType() == Material.AIR)
            return;
        if (event.getClickedBlock().getType().isInteractable())
            return;
        player.getInventory().addItem(new ItemStack(event.getClickedBlock().getType()));
        event.getClickedBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onHeal(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().isEmpty())
            return;
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), "water"))
            return;
        if (!(event.getRightClicked() instanceof Player))
            return;
        ((Player) event.getRightClicked()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 2, false));
    }

    @EventHandler
    public void onChiBlock(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().isEmpty())
            return;
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), "chi"))
            return;
        if (!(event.getRightClicked() instanceof Player))
            return;
        dropDisc((Player)event.getRightClicked());
        if (tryTakeLevel(player, 2))
            player.sendMessage("You blocked "+ event.getRightClicked().getName() + "'s Chi!");
    }

    static void dropDisc(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();

        for (ItemStack item : contents) {
            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta.hasLore()) {
                    PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
                    for (Component loreComponent : itemMeta.lore()) {
                        String loreText = serializer.serialize(loreComponent);
                        if (loreText != null && ChatColor.stripColor(loreText).equalsIgnoreCase(AvatarEffectChecker.lore)) {
                            player.getWorld().dropItem(player.getLocation().subtract(player.getLocation().getDirection().multiply(6)), item);
                            player.getInventory().remove(item);
                            player.clearActivePotionEffects();
                            player.sendMessage("Your chi has been blocked!");
                        }
                    }
                }
            }
        }
    }

    public boolean tryTakeLevel(Player player, int level) {
        if (player.getLevel() < level) {
            player.sendMessage("You don't have enough levels!");
            return false;
        }

        player.setLevel(player.getLevel() - level);
        player.sendMessage("§4-5 levels");
        return true;
    }
}
