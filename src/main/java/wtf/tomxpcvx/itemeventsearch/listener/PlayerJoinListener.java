package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Config;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
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
        ItemEventSearch.players.add(iep);

        Config config = new Config();
        FileConfiguration matchConfig = config.getConfig(iep.getPlayer().getName(), true);

        List<Integer> eventItemIds = (List<Integer>) matchConfig.getList("ItemEventSearch.Player." + iep.getPlayer().getName() + ".LocatedEventItemIds");

        if(eventItemIds != null) {
            iep.setLocatedEventItemIds(eventItemIds);
        }

        /*if(matchConfig.getString("ItemEventSearch.Player." + iep.getPlayer().getName() + ".Rank") == null)
            matchConfig.set("ItemEventSearch.Player." + iep.getPlayer().getName() + ".Rank", "Spieler");

        iep.setRank(matchConfig.getString("EventItemSearch.Player." + iep.getPlayer().getName() + ".Rank"));*/
        ScoreboardUtil.initializeScoreboard(iep);

    }

}
