package dev.tomxpcvx.itemeventsearch.domain;

import dev.tomxpcvx.itemeventsearch.ItemEventSearch;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import dev.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import dev.tomxpcvx.itemeventsearch.util.PluginUtil;

import java.util.ArrayList;
import java.util.List;

public class ItemEventPlayer {

    private List<Integer> locatedEventItemIds;
    private Player player;
    private Scoreboard scoreboard;
    private String rank;
    private int lastAlreadyMessageDelay;
    private boolean eventItemRemoveMode;

    public ItemEventPlayer(Player player) {
        this.player = player;
        this.locatedEventItemIds = new ArrayList<Integer>();
        this.lastAlreadyMessageDelay = 0;
    }

    public List<Integer> getLocatedEventItemIds() {
        return locatedEventItemIds;
    }

    public void setLocatedEventItemIds(List<Integer> locatedEventItemIds) {
        this.locatedEventItemIds = locatedEventItemIds;
    }

    public boolean addLocatedEventItemId(int id) {
        return locatedEventItemIds.add(id);
    }

    public boolean removeLocatedEventItemId(int id) {
        int index = 0;
        for(int eventItemId : locatedEventItemIds) {
            if(id == eventItemId) {
                locatedEventItemIds.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public int getLocatedEventItemCount() {
        return this.locatedEventItemIds.size();
    }

    public int getLastAlreadyMessageDelay() {
        return lastAlreadyMessageDelay;
    }

    public void setLastAlreadyMessageDelay(int lastAlreadyMessageDelay) {
        this.lastAlreadyMessageDelay = lastAlreadyMessageDelay;
    }

    public void addLastAlreadyMessageDelay(int lastAlreadyMessageDelay) {
        this.lastAlreadyMessageDelay = this.lastAlreadyMessageDelay + lastAlreadyMessageDelay;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ItemEventPlayer setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public String getRank() {
        return rank;
    }

    public ItemEventPlayer setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public boolean isEventItemRemoveMode() {
        return eventItemRemoveMode;
    }

    public void setEventItemRemoveMode(boolean eventItemRemoveMode) {
        this.eventItemRemoveMode = eventItemRemoveMode;
    }

    public String trimPlayerName(int length) {
        String playerName = this.getPlayer().getName();
        List<String> strings = new ArrayList<String>();
        for (int i=0; i < playerName.length(); i++) {
            strings.add(playerName.substring(i, Math.min(i + length, playerName.length())));
        }
        
        return strings.get(0);
    }

    public void setWin() {
        PluginUtil pluginUtil = new PluginUtil();
        if(pluginUtil.getEconomy() != null) {
            pluginUtil.getEconomy().depositPlayer(this.getPlayer().getName(), ItemEventSearchUtil.prize);
            this.getPlayer().sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("WinMoney").replace("@money", String.valueOf(ItemEventSearchUtil.prize)));
        } else {
            this.getPlayer().sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("Win"));
        }
        this.addItemEventPlayerToWinners();
    }

    public void addItemEventPlayerToWinners() {
        List<String> winners = (List<String>) ItemEventSearch.getPlugin().getConfig().get("ItemEventSearch.Winners");
        if(!winners.contains(this.getPlayer().getName())) {
            winners.add(this.getPlayer().getUniqueId().toString());
            ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.Winners", winners);
            ItemEventSearch.getPlugin().saveConfig();
        }
    }
}
