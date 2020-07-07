package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import wtf.tomxpcvx.itemeventsearch.util.ConfigUtil;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;

public class PlayerKickListener implements Listener {

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        ItemEventPlayer iep = ItemEventSearchUtil.getItemEventPlayerByName(e.getPlayer().getName());
        FileConfiguration playerConfig = ConfigUtil.getConfig(iep.getPlayer().getUniqueId().toString(), true);
        playerConfig.set("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".LocatedEventItemIds", iep.getLocatedEventItemIds());
        //matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".Rank", iep.getRank());
        ConfigUtil.saveConfig(iep.getPlayer().getUniqueId().toString(), true, playerConfig);
        ItemEventSearchUtil.removeItemEventPlayer(iep);
    }
}