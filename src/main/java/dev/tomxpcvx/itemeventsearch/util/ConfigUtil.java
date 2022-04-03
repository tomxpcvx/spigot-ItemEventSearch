package dev.tomxpcvx.itemeventsearch.util;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import dev.tomxpcvx.itemeventsearch.ItemEventSearch;
import dev.tomxpcvx.itemeventsearch.domain.EventItem;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigUtil {

    private static void saveDefaultConfig() {
        FileConfiguration configConfig = getConfig("config", false);
        configConfig.addDefault("ItemEventSearch.World", "world");
        configConfig.addDefault("ItemEventSearch.PluginDisplayName", "&3&o&lItemEventSearch>&b  ");
        configConfig.addDefault("ItemEventSearch.EventItemName", "EventItem");
        configConfig.addDefault("ItemEventSearch.EventItemMaterial", Material.COOKIE.toString());
        configConfig.addDefault("ItemEventSearch.EventItemCount", 0);
        configConfig.addDefault("ItemEventSearch.Prize", 5000.0);
        configConfig.addDefault("ItemEventSearch.PickupSound", Sound.ENTITY_PLAYER_BURP.toString());
        configConfig.addDefault("ItemEventSearch.PickupEffect", Effect.MOBSPAWNER_FLAMES.toString());
        configConfig.addDefault("ItemEventSearch.Winners", new ArrayList<String>());
        configConfig.addDefault("ItemEventSearch.Scoreboard.Title", "&3&o&lEventItem-Counter");
        configConfig.addDefault("ItemEventSearch.Scoreboard.EventItemFind", "&b&oFound:");
        configConfig.addDefault("ItemEventSearch.Scoreboard.EventItemExist", "&b&oHidden:");
        configConfig.addDefault("ItemEventSearch.Message.CommandGiveEventItem", "You have received 64 event items.");
        configConfig.addDefault("ItemEventSearch.Message.CommandGiveWrongWorld", "You cannot use this command in this world.");
        configConfig.addDefault("ItemEventSearch.Message.CommandWorldSet", "You have set the event world to &c@WORLD&b.");
        configConfig.addDefault("ItemEventSearch.Message.CommandPrizeSet", "You have &set the event reward to &c@MONEY.");
        configConfig.addDefault("ItemEventSearch.Message.CommandWinners", "The following players have found all event items:");
        configConfig.addDefault("ItemEventSearch.Message.CommandWinnersPrefixPlayerNames", "&b - ");
        configConfig.addDefault("ItemEventSearch.Message.CommandRemoveEnabled", "You can now remove event items.");
        configConfig.addDefault("ItemEventSearch.Message.CommandRemoveDisabled", "You can now no longer remove event items.");
        configConfig.addDefault("ItemEventSearch.Message.CommandNotFound", "This command does not exist.");
        configConfig.addDefault("ItemEventSearch.Message.RemovedEventItem", "You have removed event item &c@NR&b.");
        configConfig.addDefault("ItemEventSearch.Message.PickupEventItem", "You have found event item &c@NR&b!");
        configConfig.addDefault("ItemEventSearch.Message.EventItemDrop", "You have placed event item no. &c@NR&b!");
        configConfig.addDefault("ItemEventSearch.Message.EventItemPickUp1", "You have found your &c@NR &bevent item.");
        configConfig.addDefault("ItemEventSearch.Message.EventItemPickUp2", "You now have &c@NR &bof &c@MAX &bevent items.");
        configConfig.addDefault("ItemEventSearch.Message.Already", "You have already found this event item.");
        configConfig.addDefault("ItemEventSearch.Message.Win", "You have found all event items!");
        configConfig.addDefault("ItemEventSearch.Message.WinMoney", "You have found all event items! As a reward you will receive &c@money &bmoney.");
        configConfig.options().copyDefaults(true);
        saveConfig("config", false, configConfig);
    }

    private static void saveDefaultItemConfig() {
        FileConfiguration itemConfig = getConfig("items", false);
        itemConfig.addDefault("ItemEventSearch.EventItems", new ArrayList<>());
        itemConfig.options().copyDefaults(true);
        saveConfig("items", false, itemConfig);
    }

    private static void registerDefaultMessages(PluginUtil pluginUtil) {
        FileConfiguration configConfig = getConfig("config", false);
        pluginUtil.registerMessage("CommandGiveEventItem", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandGiveEventItem")));
        pluginUtil.registerMessage("CommandGiveWrongWorld", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandGiveWrongWorld")));
        pluginUtil.registerMessage("CommandWorldSet", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandWorldSet")));
        pluginUtil.registerMessage("CommandPrizeSet", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandPrizeSet")));
        pluginUtil.registerMessage("CommandWinners", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandWinners")));
        pluginUtil.registerMessage("CommandWinnersPrefixPlayerNames", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandWinnersPrefixPlayerNames")));
        pluginUtil.registerMessage("CommandRemoveEnabled", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandRemoveEnabled")));
        pluginUtil.registerMessage("CommandRemoveDisabled", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandRemoveDisabled")));
        pluginUtil.registerMessage("CommandNotFound", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.CommandNotFound")));
        pluginUtil.registerMessage("Scoreboard.Title", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Scoreboard.Title")));
        pluginUtil.registerMessage("Scoreboard.EventItemFind", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Scoreboard.EventItemFind")));
        pluginUtil.registerMessage("Scoreboard.EventItemExist", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Scoreboard.EventItemExist")));
        pluginUtil.registerMessage("EventItemName", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.EventItemName")));
        pluginUtil.registerMessage("EventItemMaterial", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.EventItemMaterial")));
        pluginUtil.registerMessage("EventItemCount", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.EventItemCount")));
        pluginUtil.registerMessage("RemovedEventItem", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.RemovedEventItem")));
        pluginUtil.registerMessage("PickupEventItem", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.PickupEventItem")));
        pluginUtil.registerMessage("PickupSound", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.PickupSound")));
        pluginUtil.registerMessage("PickupEffect", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.PickupEffect")));
        pluginUtil.registerMessage("EventItemDrop", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.EventItemDrop")));
        pluginUtil.registerMessage("EventItemPickUp1", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.EventItemPickUp1")));
        pluginUtil.registerMessage("EventItemPickUp2", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.EventItemPickUp2")));
        pluginUtil.registerMessage("Already", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.Already")));
        pluginUtil.registerMessage("Win", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.Win")));
        pluginUtil.registerMessage("WinMoney", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Message.WinMoney")));
        pluginUtil.registerMessage("Prize", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.Prize")));
        pluginUtil.registerMessage("PluginDisplayName", PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.PluginDisplayName")));
    }

    private static void getDefaultConfig() {
        FileConfiguration configConfig = getConfig("config", false);
        ItemEventSearchUtil.eventItemCount = Integer.parseInt(configConfig.getString("ItemEventSearch.EventItemCount"));
        ItemEventSearchUtil.eventItemMaterial = Material.getMaterial(configConfig.get("ItemEventSearch.EventItemMaterial").toString());
        ItemEventSearchUtil.eventItemName = configConfig.getString("ItemEventSearch.EventItemName");
        ItemEventSearchUtil.prize = configConfig.getDouble("ItemEventSearch.Prize");
        PluginUtil.pluginDisplayName = PluginUtil.translateColorCodes(configConfig.getString("ItemEventSearch.PluginDisplayName"));

        FileConfiguration itemConfig = getConfig("items", false);
        for(Map<?, ?> eventItem : itemConfig.getMapList("ItemEventSearch.EventItems"))
            ItemEventSearchUtil.eventItems.add(new EventItem((Map<String, Object>) eventItem));

        String worldName = configConfig.getString("ItemEventSearch.World");
        World world = Bukkit.getWorld(worldName != null ? worldName : "world");

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(world != null && player.getWorld() == world) {
                ItemEventPlayer iep = new ItemEventPlayer(player);
                FileConfiguration playerConfig = getConfig(player.getName(), true);
                List<Integer> eventItemIds = (ArrayList<Integer>) playerConfig.getList("ItemEventSearch.Player." + player.getName() + ".LocatedEventItemIds");
                iep.setLocatedEventItemIds(eventItemIds);
                ScoreboardUtil.initializeScoreboard(iep);
                ItemEventSearchUtil.players.add(iep);
            }
        }
    }

    public static void registerConfigurations(PluginUtil pluginUtil) {
        saveDefaultConfig();
        saveDefaultItemConfig();
        getDefaultConfig();
        registerDefaultMessages(pluginUtil);
    }

    public static FileConfiguration createConfig(String name, boolean isPlayer) {
        if(!name.endsWith(".yml")) {
            name = name + ".yml";
        }

        File arena;
        if(!isPlayer) {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator +  name);
        } else {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator + "player" + File.separator +  name);
        }

        if(!arena.exists()) {
            ItemEventSearch.getPlugin().getDataFolder().mkdir();
            try {
                arena.getParentFile().mkdirs();
                arena.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(arena);
    }

    public static void saveConfig(String name, boolean isPlayer, FileConfiguration config) {
        if(!name.endsWith(".yml")) {
            name = name + ".yml";
        }

        File arena;
        if(!isPlayer) {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator +  name);
        } else {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator + "player", name);
        }

        try {
            config.save(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig(String name, boolean isPlayer) {
        if(!name.endsWith(".yml")) {
            name = name + ".yml";
        }

        createConfig(name, isPlayer);

        File arena;
        if(!isPlayer) {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator + name);
        } else {
            arena = new File(ItemEventSearch.getPlugin().getDataFolder() + File.separator + "player", name);
        }
        return YamlConfiguration.loadConfiguration(arena);
    }

    public static void saveAllPlayersData() {
        if(!ItemEventSearchUtil.players.isEmpty()) {
            for (ItemEventPlayer iep : ItemEventSearchUtil.players) {
                String uuid = iep.getPlayer().getUniqueId().toString();
                FileConfiguration playerConfig = getConfig(uuid, true);
                playerConfig.set("ItemEventSearch.Player." + uuid + ".LocatedEventItemIds", iep.getLocatedEventItemIds());
                saveConfig(uuid, true, playerConfig);
            }
        }
    }

    public static void saveAllEventItems() {
        List<Map<String, Object>> eventItems = new ArrayList<Map<String, Object>>();
        for(EventItem item : ItemEventSearchUtil.eventItems) {
            eventItems.add(item.serialize());
        }
        FileConfiguration itemConfig = ConfigUtil.getConfig("items", false);
        itemConfig.set("ItemEventSearch.EventItems", eventItems);
        ConfigUtil.saveConfig("items", false, itemConfig);
    }
}
