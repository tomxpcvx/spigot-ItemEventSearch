package wtf.tomxpcvx.itemeventsearch.util;

import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PluginUtil {

    public static String pluginName = "ItemEventSearch";
    public static String pluginConsoleName = "[" + pluginName + "] ";
    public static String pluginDisplayName;
    private Economy economy;
    private Permission permission;

    public PluginUtil() {}

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = ItemEventSearch.getPlugin().getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp != null) {
            this.economy = rsp.getProvider();
            return true;
        }
        return false;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = ItemEventSearch.getPlugin().getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp != null) {
            this.permission = rsp.getProvider();
            return true;
        }
        return false;
    }

    public void registerExtensions() {
        if(ItemEventSearch.getPlugin().getServer().getPluginManager().getPlugin("Vault") != null) {
            if(!setupEconomy()) printlnToConsole("No economy plugin detected!");
            if(!setupPermissions()) printlnToConsole("No permission plugin detected!");
        } else {
            printlnToConsole("Plugin 'Vault' is missing!");
        }
    }

    public void registerMessage(String key, String message) {
        ItemEventSearchUtil.messages.put(key, message);
    }

    public static void printlnToConsole(String message) {
        System.out.println(pluginConsoleName + message);
    }

    public static String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public Economy getEconomy() {
        return this.economy;
    }

    public Permission getPermission() {
        return this.permission;
    }

}
