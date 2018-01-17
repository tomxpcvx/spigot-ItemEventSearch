package wtf.tomxpcvx.itemeventsearch.util;

import wtf.tomxpcvx.itemeventsearch.Constants;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PluginUtil {

    private ItemEventSearch plugin;
    public Economy econ;
    public Permission perms;

    public PluginUtil() {
        this.plugin = ItemEventSearch.getPlugin();
    }

    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = rsp.getProvider();
        return this.econ != null;
    }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        this.perms = rsp.getProvider();
        return this.perms != null;
    }

    public void printlnToConsole(String message) {
        System.out.println(Constants.pluginConsoleName + message);
    }

    public String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void registerMessage(String key, String message) {
        Constants.msg.put(key, message);
    }


}
