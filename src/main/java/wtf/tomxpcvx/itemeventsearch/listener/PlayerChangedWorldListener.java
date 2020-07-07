package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import wtf.tomxpcvx.itemeventsearch.util.ConfigUtil;
import wtf.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        ItemEventPlayer iep = new ItemEventPlayer(e.getPlayer());
        World w = Bukkit.getWorld(ItemEventSearch.getPlugin().getConfig().getString("ItemEventSearch.World"));
        if(w != null && iep.getPlayer().getWorld() == w) {
            FileConfiguration playerConfig = ConfigUtil.getConfig(iep.getPlayer().getUniqueId().toString(), true);
            List<Integer> eventItemIds = (List<Integer>) playerConfig.getList("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".LocatedEventItemIds");
            iep.setLocatedEventItemIds(eventItemIds);
            ScoreboardUtil.create(iep);
        } else {
            ScoreboardUtil.leave(iep);
        }

    }

}
