package wtf.tomxpcvx.itemeventsearch;

import wtf.tomxpcvx.itemeventsearch.util.*;
import me.rojetto.comfy.bukkit.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemEventSearch extends JavaPlugin {

    private static ItemEventSearch plugin;
    private static BukkitCommandManager bukkitCommandManager;
    public static PluginUtil pluginUtil;

    public void onEnable() {
        plugin = this;
        bukkitCommandManager = new BukkitCommandManager(plugin);
        pluginUtil = new PluginUtil();
        pluginUtil.registerExtensions();

        CommandUtil.registerCommands(bukkitCommandManager);
        EventUtil.registerEvents(plugin);
        ConfigUtil.registerConfigurations(pluginUtil);
    }

    public void onDisable() {
        ConfigUtil.saveAllPlayersData();
        ConfigUtil.saveAllEventItems();
    }

    public static ItemEventSearch getPlugin() {
        return plugin;
    }

    public static BukkitCommandManager getBukkitCommandManager() {
        return bukkitCommandManager;
    }

}