package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import wtf.tomxpcvx.itemeventsearch.util.ConfigUtil;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import wtf.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ItemEventPlayer iep = new ItemEventPlayer(e.getPlayer());
        ItemEventSearchUtil.players.add(iep);
        System.out.println(iep.getPlayer().getUniqueId());
        FileConfiguration playerConfig = ConfigUtil.getConfig(iep.getPlayer().getUniqueId().toString(), true);

        List<Integer> eventItemIds = (List<Integer>) playerConfig.getList("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".LocatedEventItemIds");

        if(eventItemIds != null) {
            iep.setLocatedEventItemIds(eventItemIds);
        }

        /*if(matchConfig.getString("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".Rank") == null)
            matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".Rank", "Spieler");

        iep.setRank(matchConfig.getString("EventItemSearch.Player." + iep.getPlayer().getUniqueId().toString() + ".Rank"));*/
        ScoreboardUtil.initializeScoreboard(iep);

    }

}
