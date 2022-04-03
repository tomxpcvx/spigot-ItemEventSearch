package dev.tomxpcvx.itemeventsearch.util;

import dev.tomxpcvx.itemeventsearch.domain.EventItem;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemEventSearchUtil {

    public static List<ItemEventPlayer> players = new ArrayList<>();
    public static List<EventItem> eventItems = new ArrayList<>();
    public static int eventItemCount;
    public static String eventItemName;
    public static Material eventItemMaterial;
    public static double prize;
    public static HashMap<String, String> messages = new HashMap<String, String>();

    public static ItemEventPlayer getItemEventPlayerByName(String playerName) {
        for(ItemEventPlayer iep : players) {
            if(iep.getPlayer().getName().equals(playerName)) {
                return iep;
            }
        }
        return null;
    }

    public static void setItemEventPlayer(ItemEventPlayer iep) {
        int index = 0;
        for(ItemEventPlayer eventPlayer : players) {
            if(eventPlayer.getPlayer().getName().equals(iep.getPlayer().getName())) {
                players.set(index, iep);
            }
            index++;
        }
    }

    public static void removeItemEventPlayer(ItemEventPlayer iep) {
        boolean removePlayer = false;

        for(ItemEventPlayer eventPlayer : players) {
            if(eventPlayer.getPlayer().getName().equals(iep.getPlayer().getName())) {
                removePlayer = true;
            }
        }

        if(removePlayer)
            players.remove(iep);
    }
}
