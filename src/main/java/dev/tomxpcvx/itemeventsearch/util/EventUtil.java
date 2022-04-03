package dev.tomxpcvx.itemeventsearch.util;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import dev.tomxpcvx.itemeventsearch.listener.*;

public class EventUtil {

    public static void registerEvents(JavaPlugin plugin) {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new AsyncPlayerChatListener(), plugin);
        manager.registerEvents(new ItemDespawnListener(), plugin);
        manager.registerEvents(new PlayerChangedWorldListener(), plugin);
        manager.registerEvents(new PlayerDropItemListener(), plugin);
        manager.registerEvents(new PlayerJoinListener(), plugin);
        manager.registerEvents(new PlayerPickupItemListener(), plugin);
        manager.registerEvents(new PlayerQuitListener(), plugin);
        manager.registerEvents(new PlayerKickListener(), plugin);
        manager.registerEvents(new PlayerCommandPreprocessListener(), plugin);
    }
}
