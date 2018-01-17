package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Config;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerKickListener implements Listener {

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        ItemEventPlayer iep = ItemEventSearch.getItemEventPlayerByName(e.getPlayer().getName());
        Config config = new Config();
        FileConfiguration matchConfig = config.getConfig(iep.getPlayer().getName(), true);
        matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getName() + ".LocatedEventItemIds", iep.getLocatedEventItemIds());
        //matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getName() + ".Rank", iep.getRank());
        config.saveConfig(iep.getPlayer().getName(), true, matchConfig);
        ItemEventSearch.removeItemEventPlayer(iep);
    }
}