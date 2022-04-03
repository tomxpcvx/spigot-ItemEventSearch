package dev.tomxpcvx.itemeventsearch.listener;

import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import dev.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {

        ItemEventPlayer iep = ItemEventSearchUtil.getItemEventPlayerByName(e.getPlayer().getName());

        e.setFormat(ChatColor.YELLOW + "[" + iep.getLocatedEventItemCount() + "] " + ChatColor.RESET + e.getFormat());

    }

}
