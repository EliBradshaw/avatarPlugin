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
        Player player = null;
        if (!(sender instanceof Player) && args.length != 2) {
            sender.sendMessage("Too many or too few arguments");
            return true;
        } else if (sender instanceof Player) {
            player = (Player) sender; // Give the disc to the command sender by default
            if (!player.isOp()) {
                sender.sendMessage("You're not opped silly!");
                return true;
            }
        }

        if (args.length == 2) {
            String playerName = args[1];
            player = Bukkit.getPlayer(playerName);
            if (player == null) {
                sender.sendMessage("Player not found or not online!");
                return true;
            }
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage("Usage: /givedisc <disc_name> [player]");
            return true;
        }

        String discName = args[0].toLowerCase(); // Assuming the disc name is provided as the first argument
        // Give the specified disc to the target player
        ItemStack disc = DiscData.getDisc(discName).createItem();
        if (disc == null) {
            sender.sendMessage("\""+discName+"\" is not a valid element name");
            return true;
        }
        if (player == null)
            return false;

        player.getInventory().addItem(disc);
        player.sendMessage("You received the " + discName + " disc!");

        // Apply effects associated with the disc to the target player
        AvatarEffectChecker.checkMusicDiscs(player);

        return true;
    }
}
