package wtf.tomxpcvx.itemeventsearch;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    public JavaPlugin plugin;

    public Config() {
        this.plugin = ItemEventSearch.getPlugin();
    }


    public FileConfiguration createConfig(String name, boolean isPlayer) {
        if (!name.endsWith(".yml")) {
            name = name + ".yml";
        }

        File arena = new File(this.plugin.getDataFolder() + File.separator + "player" + File.separator +  name);

        if(!isPlayer)
            arena = new File(this.plugin.getDataFolder() + File.separator +  name);


        if (!arena.exists()) {
            this.plugin.getDataFolder().mkdir();
            try {
                arena.getParentFile().mkdirs();
                arena.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(arena);
    }

    public void saveConfig(String name, boolean isPlayer, FileConfiguration config) {
        if (!name.endsWith(".yml")) {
            name = name + ".yml";
        }
        File arena = new File(this.plugin.getDataFolder() + File.separator + "player", name);
        if(!isPlayer)
            arena = new File(this.plugin.getDataFolder() + File.separator +  name);

        try {
            config.save(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(String name, boolean isPlayer) {
        if (!name.endsWith(".yml")) {
            name = name + ".yml";
        }
        createConfig(name, isPlayer);
        File arena = new File(this.plugin.getDataFolder() + File.separator + "player", name);
        if(!isPlayer)
            arena = new File(this.plugin.getDataFolder() + File.separator +  name);

        return YamlConfiguration.loadConfiguration(arena);
    }
}
