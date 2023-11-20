package org.ridgerider.me.avatarnations;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XPGiver implements CommandExecutor {

    private final AvatarNations plugin;

    public XPGiver(AvatarNations plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You're not allowed to use this command as console");
            return true;
        }

        Player player = (Player) sender;
        Player target;
        if (args.length == 2) {
            String playerName = args[0];
            target = Bukkit.getPlayer(playerName);
            if (target == null) {
                sender.sendMessage("Player not found or not online!");
                return true;
            }
        } else {
            sender.sendMessage("Usage: /xpgive <player_name> <level_amount>");
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage("Usage: /xpgive <player_name> <level_amount>");
            return true;
        }
        if (!isInteger(args[1])) {
            sender.sendMessage("Level amount must be a whole number!");
            return true;
        }
        int amount = Integer.parseInt(args[1]);
        if (amount <= 0) {
            sender.sendMessage("Level amount must be bigger than zero!");
            return true;
        }
        if (amount > player.getLevel()) {
            sender.sendMessage("You don't have that many levels!");
            return true;
        }

        player.setLevel(player.getLevel()-amount);
        player.sendMessage("Gave " + amount + " levels to " + target.getName());
        target.setLevel(target.getLevel()+amount);
        target.sendMessage("Received " + amount + " levels from " + player.getName());
        AvatarEffectChecker.checkMusicDiscs(player);

        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
