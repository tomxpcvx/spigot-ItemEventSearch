package dev.tomxpcvx.itemeventsearch.listener;

import dev.tomxpcvx.itemeventsearch.util.ConfigUtil;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import dev.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import dev.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
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
