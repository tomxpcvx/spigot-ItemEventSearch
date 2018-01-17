package wtf.tomxpcvx.itemeventsearch;

import wtf.tomxpcvx.itemeventsearch.command.CommandItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.listener.*;
import wtf.tomxpcvx.itemeventsearch.util.EventItem;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import wtf.tomxpcvx.itemeventsearch.util.PluginUtil;
import wtf.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import me.rojetto.comfy.argument.BooleanType;
import me.rojetto.comfy.argument.FloatType;
import me.rojetto.comfy.bukkit.BukkitCommandManager;
import me.rojetto.comfy.tree.Argument;
import me.rojetto.comfy.tree.Literal;
import net.milkbowl.vault.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemEventSearch extends JavaPlugin {

    private static ItemEventSearch plugin;
    public static List<ItemEventPlayer> players;
    public static List<EventItem> eventItems;

    public static FileConfiguration matchConfig;
    public static PluginUtil pluginUtil;

    private BukkitCommandManager bukkitCommandManager;

    public void onEnable() {

        plugin = this;
        pluginUtil = new PluginUtil();
        players = new ArrayList<ItemEventPlayer>();
        eventItems = new ArrayList<EventItem>();


        registerCommands();

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new AsyncPlayerChatListener(), this);
        manager.registerEvents(new ItemDespawnListener(), this);
        manager.registerEvents(new PlayerChangedWorldListener(), this);
        manager.registerEvents(new PlayerDropItemListener(), this);
        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new PlayerPickupItemListener(), this);
        manager.registerEvents(new PlayerQuitListener(), this);
        manager.registerEvents(new PlayerKickListener(), this);
        manager.registerEvents(new PlayerCommandPreprocessListener(), this);

        loadConfig();


        Constants.eventItemCount = Integer.parseInt(getConfig().getString("ItemEventSearch.EventItemCount"));
        Constants.eventItemMaterial = Material.getMaterial(getConfig().get("ItemEventSearch.EventItemMaterial").toString());
        Constants.eventItemName = getConfig().getString("ItemEventSearch.EventItemName");
        Constants.prize = getConfig().getDouble("ItemEventSearch.Prize");

        matchConfig = new Config().getConfig("items", false);

        if(matchConfig.getMapList("ItemEventSearch.EventItems") != null) {
            for(Map itemMap : matchConfig.getMapList("ItemEventSearch.EventItems"))
                eventItems.add(new EventItem(itemMap));
        }

        World w = Bukkit.getWorld(getConfig().getString("ItemEventSearch.World"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (w != null && player.getWorld() == w) {
                ItemEventPlayer iep = new ItemEventPlayer(player);
                matchConfig = new Config().getConfig(player.getName(), true);
                List<Integer> eventItemIds = (ArrayList<Integer>) matchConfig.getList("ItemEventSearch.Player." + player.getName() + ".LocatedEventItemIds");
                iep.setLocatedEventItemIds(eventItemIds);
                ScoreboardUtil.initializeScoreboard(iep);
                players.add(iep);
            }
        }


        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }

        if (!pluginUtil.setupEconomy()) {
            pluginUtil.printlnToConsole("No economy plugin detected!");
        }

        if (!pluginUtil.setupPermissions()) {
            pluginUtil.printlnToConsole("No permission plugin detected!");
        }

        pluginUtil.registerMessage("CommandGiveEventItem", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandGiveEventItem")));
        pluginUtil.registerMessage("CommandGiveWrongWorld", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandGiveWrongWorld")));
        pluginUtil.registerMessage("CommandWorldSet", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandWorldSet")));
        pluginUtil.registerMessage("CommandPrizeSet", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandPrizeSet")));
        pluginUtil.registerMessage("CommandRemoveEnabled", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandRemoveEnabled")));
        pluginUtil.registerMessage("CommandRemoveDisabled", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandRemoveDisabled")));
        pluginUtil.registerMessage("CommandNotFound", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.CommandNotFound")));
        pluginUtil.registerMessage("Scoreboard.Title", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Scoreboard.Title")));
        pluginUtil.registerMessage("Scoreboard.EventItemFind", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Scoreboard.EventItemFind")));
        pluginUtil.registerMessage("Scoreboard.EventItemExist", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Scoreboard.EventItemExist")));
        pluginUtil.registerMessage("EventItemName", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.EventItemName")));
        pluginUtil.registerMessage("EventItemMaterial", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.EventItemMaterial")));
        pluginUtil.registerMessage("EventItemCount", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.EventItemCount")));
        pluginUtil.registerMessage("RemovedEventItem", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.RemovedEventItem")));
        pluginUtil.registerMessage("PickupEventItem", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.PickupEventItem")));
        pluginUtil.registerMessage("EventItemDrop", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.EventItemDrop")));
        pluginUtil.registerMessage("EventItemPickUp1", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.EventItemPickUp1")));
        pluginUtil.registerMessage("EventItemPickUp2", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.EventItemPickUp2")));
        pluginUtil.registerMessage("Already", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.Already")));
        pluginUtil.registerMessage("Win", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.Win")));
        pluginUtil.registerMessage("WinMoney", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Message.WinMoney")));
        pluginUtil.registerMessage("Prize", pluginUtil.translateColorCodes(getConfig().getString("ItemEventSearch.Prize")));


    }

    public void onDisable() {
        if(players != null) {
            for (ItemEventPlayer iep : players) {
                Config config = new Config();
                matchConfig = config.getConfig(iep.getPlayer().getName(), true);
                matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getName() + ".LocatedEventItemIds", iep.getLocatedEventItemIds());
                config.saveConfig(iep.getPlayer().getName(), true, matchConfig);
            }
        }

        List itemMapList = new ArrayList();
        for(EventItem item : eventItems) {
            itemMapList.add(item.serialize());
        }
        Config config = new Config();
        matchConfig = config.getConfig("items", false);
        matchConfig.set("ItemEventSearch.EventItems", itemMapList);
        config.saveConfig("items", false, matchConfig);
    }

    public static ItemEventSearch getPlugin() {
        return ItemEventSearch.plugin;
    }

    private void registerCommandListeners() {
        bukkitCommandManager.addListener(new CommandItemEventSearch());
    }

    private void registerItemEventSearchCommand() {
        bukkitCommandManager.addCommand(new Literal("itemeventsearch", "ies", "itemevent")
                .then(new Literal("give", "g")
                                .description("Gives the player the event item")
                                .executes("itemeventsearch.command.give")
                                .permission("itemeventsearch.admin"))
                .then(new Literal("world", "w")
                                .description("Set the event world")
                                .executes("itemeventsearch.command.world")
                                .permission("itemeventsearch.admin"))
                .then(new Literal("prize", "p")
                        .then(new Argument("prize", new FloatType())
                                .description("Set the winning prize")
                                .executes("itemeventsearch.command.prize"))
                                .permission("itemeventsearch.admin"))
                .then(new Literal("remove", "r", "rm")
                        .then(new Argument("state", new BooleanType(new String[]{"true", "on", "yes", "enabled", "wahr", "ja", "aktiviert", "an"}, new String[]{"false", "off", "no", "disabled", "falsch", "nein", "deaktiviert", "aus"}))
                                .description("Set the remove mode")
                                .executes("itemeventsearch.command.remove"))
                                .permission("itemeventsearch.admin")));
    }

    private void registerCommands() {
        bukkitCommandManager = new BukkitCommandManager(getPlugin());

        registerCommandListeners();

        registerItemEventSearchCommand();

        bukkitCommandManager.registerCommands();
    }

    public BukkitCommandManager getBukkitCommandManager() {
        return bukkitCommandManager;
    }

    private void loadConfig() {
        getConfig().addDefault("ItemEventSearch.World", "world");
        getConfig().addDefault("ItemEventSearch.EventItemName", "Keks");
        getConfig().addDefault("ItemEventSearch.EventItemMaterial", Material.COOKIE.toString());
        getConfig().addDefault("ItemEventSearch.EventItemCount", 0);
        getConfig().addDefault("ItemEventSearch.Prize", 5000.0);
        getConfig().addDefault("ItemEventSearch.Scoreboard.Title", "&3&o&lKeks-Counter");
        getConfig().addDefault("ItemEventSearch.Scoreboard.EventItemFind", "&b&oGefundene:");
        getConfig().addDefault("ItemEventSearch.Scoreboard.EventItemExist", "&b&oVersteckte:");
        getConfig().addDefault("ItemEventSearch.Message.CommandGiveEventItem", "Du hast 64 Event-Items erhalten.");
        getConfig().addDefault("ItemEventSearch.Message.CommandGiveWrongWorld", "In dieser Welt kannst du diesen Befehl nicht nutzen.");
        getConfig().addDefault("ItemEventSearch.Message.CommandWorldSet", "Du hast die Event-Welt auf &c@WORLD &fgesetzt.");
        getConfig().addDefault("ItemEventSearch.Message.CommandPrizeSet", "Du hast die Event-Belohnung auf &c@MONEY &fgesetzt.");
        getConfig().addDefault("ItemEventSearch.Message.CommandRemoveEnabled", "Du kannst nun Event-Items entfernen.");
        getConfig().addDefault("ItemEventSearch.Message.CommandRemoveDisabled", "Du kannst nun keine Event-Items mehr entfernen.");
        getConfig().addDefault("ItemEventSearch.Message.CommandNotFound", "Dieses Kommando existiert nicht.");
        getConfig().addDefault("ItemEventSearch.Message.RemovedEventItem", "Du hast Event-Item &c@NR &bentfernt.");
        getConfig().addDefault("ItemEventSearch.Message.PickupEventItem", "Du hast Event-Item &c@NR&b gefunden!");
        getConfig().addDefault("ItemEventSearch.Message.EventItemDrop", "Du hast Event-Item Nr. &c@NR&b plaziert!");
        getConfig().addDefault("ItemEventSearch.Message.EventItemPickUp1", "Du hast dein &c@NRtes &bEvent-Item gefunden.");
        getConfig().addDefault("ItemEventSearch.Message.EventItemPickUp2", "Du hast nun &c@NR &bvon &c@MAX &bEvent-Items.");
        getConfig().addDefault("ItemEventSearch.Message.Already", "Du hast dieses Event-Item bereits gefunden.");
        getConfig().addDefault("ItemEventSearch.Message.Win", "Du hast alle Event-Items gefunden!");
        getConfig().addDefault("ItemEventSearch.Message.WinMoney", "Du hast alle Event-Items gefunden! Als Belohnung erhältst du &c@money &bGeld.");

        FileConfiguration cfg = getConfig();
        cfg.options().copyDefaults(true);
        saveConfig();

        Config config = new Config();
        matchConfig = config.getConfig("items", false);
        matchConfig.addDefault("ItemEventSearch.EventItems", new ArrayList<>());
        matchConfig.options().copyDefaults(true);
        config.saveConfig("items", false, matchConfig);

    }

    public static ItemEventPlayer getItemEventPlayerByName(String playerName) {
        for(ItemEventPlayer iep : players) {
            if(iep.getPlayer().getName().equals(playerName)) {
                return iep;
            }
        }
        return null;
    }

    public static void setItemEventPlayer(ItemEventPlayer iep) {
        int index = 0;
        for(ItemEventPlayer eventPlayer : players) {
            if(eventPlayer.getPlayer().getName().equals(iep.getPlayer().getName())) {
                players.set(index, iep);
            }
            index++;
        }
    }

    public static void removeItemEventPlayer(ItemEventPlayer iep) {
        boolean removePlayer = false;

        for(ItemEventPlayer eventPlayer : players) {
            if(eventPlayer.getPlayer().getName().equals(iep.getPlayer().getName())) {
                removePlayer = true;
            }
        }

        if(removePlayer)
            players.remove(iep);
    }
}