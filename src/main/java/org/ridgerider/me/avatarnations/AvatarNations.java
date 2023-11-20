package org.ridgerider.me.avatarnations;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class AvatarNations extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("AvatarNations has been enabled!");

        // Registering a command
        getCommand("givedisc").setExecutor(new AvatarCommandExecutor(this));

        // Calling AvatarEffectChecker every second
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                AvatarEffectChecker.checkMusicDiscs(player);
            }
        }, 0L, 20L); // 20 ticks = 1 second

        getServer().getPluginManager().registerEvents(new AvatarAbilityHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("AvatarNations has been disabled!");
    }
}
