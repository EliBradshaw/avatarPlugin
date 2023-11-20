package org.ridgerider.me.avatarnations;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class AvatarCommandExecutor implements CommandExecutor {

    private final AvatarNations plugin;

    public AvatarCommandExecutor(AvatarNations plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) ) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            sender.sendMessage("You're not opped silly!");
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage("Usage: /givedisc <disc_name> [player]");
            return true;
        }

        String discName = args[0].toLowerCase(); // Assuming the disc name is provided as the first argument

        Material discMaterial;
        switch (discName) {
            case "fire":
                discMaterial = Material.MUSIC_DISC_PIGSTEP;
                break;
            case "water":
                discMaterial = Material.MUSIC_DISC_OTHERSIDE;
                break;
            case "air":
                discMaterial = Material.MUSIC_DISC_STRAD;
                break;
            case "earth":
                discMaterial = Material.MUSIC_DISC_WAIT;
                break;
            default:
                sender.sendMessage("Invalid disc name!");
                return true;
        }

        Player targetPlayer;
        if (args.length == 2) {
            String playerName = args[1];
            targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                sender.sendMessage("Player not found or not online!");
                return true;
            }
        } else {
            targetPlayer = player; // Give the disc to the command sender by default
        }

        // Give the specified disc to the target player
        ItemStack disc = new ItemStack(discMaterial);
        ItemMeta meta = disc.getItemMeta();
        if (meta != null) {
            String displayName = discName.substring(0, 1).toUpperCase() + discName.substring(1) + " Nation";

// Create the display name without italic formatting
            Component displayNameComponent = Component.text(displayName)
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);

// Create lore for the item
            ArrayList<String> lores = new ArrayList<>();
            lores.add(AvatarEffectChecker.lore);

// Set the display name and lore for the item
            meta.displayName(displayNameComponent);
            meta.setLore(lores);
            disc.setItemMeta(meta);
        }

        targetPlayer.getInventory().addItem(disc);
        targetPlayer.sendMessage("You received the " + discName + " disc!");

        // Apply effects associated with the disc to the target player
        AvatarEffectChecker.checkMusicDiscs(targetPlayer);

        return true;
    }
}
