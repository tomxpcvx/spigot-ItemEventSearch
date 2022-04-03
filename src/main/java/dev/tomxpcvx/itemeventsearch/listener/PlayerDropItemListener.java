package dev.tomxpcvx.itemeventsearch.listener;

import dev.tomxpcvx.itemeventsearch.ItemEventSearch;
import dev.tomxpcvx.itemeventsearch.domain.EventItem;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import dev.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import dev.tomxpcvx.itemeventsearch.util.PluginUtil;
import dev.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        ItemEventPlayer iep = new ItemEventPlayer(e.getPlayer());

        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName() != null) {
            if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ItemEventSearchUtil.eventItemName) && e.getItemDrop().getItemStack().getType().equals(ItemEventSearchUtil.eventItemMaterial)) {
                if(iep.getPlayer().hasPermission("itemeventsearch.admin")) {
                    ItemEventSearchUtil.eventItemCount += 1;
                    ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.EventItemCount", ItemEventSearchUtil.eventItemCount);
                    ItemEventSearch.getPlugin().saveConfig();

                    ItemStack is = e.getItemDrop().getItemStack();
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(ItemEventSearchUtil.eventItemName + "=" + ItemEventSearchUtil.eventItemCount);
                    is.setItemMeta(im);

                    EventItem ei = new EventItem(is);
                    ItemEventSearchUtil.eventItems.add(ei);

                    String s = ItemEventSearchUtil.messages.get("EventItemDrop").replace("@NR", String.valueOf(ItemEventSearchUtil.eventItemCount));
                    iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + s);
                    e.getItemDrop().setItemStack(ei.getItemStack());
                    e.getItemDrop().setTicksLived(1);

                    for(ItemEventPlayer allIep : ItemEventSearchUtil.players) {
                        ScoreboardUtil.update(allIep);
                    }

                } else {
                    e.setCancelled(true);
                }
            }
        }

    }

}
