package org.ridgerider.me.avatarnations;

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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), Material.MUSIC_DISC_PIGSTEP, "Fire Nation"))
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
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), Material.MUSIC_DISC_WAIT, "Earth Nation"))
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
        if (!AvatarEffectChecker.hasMusicDisc(player.getInventory(), Material.MUSIC_DISC_OTHERSIDE, "Water Nation"))
            return;
        if (!(event.getRightClicked() instanceof Player))
            return;
        ((Player) event.getRightClicked()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50, 2, false));
    }
}
