package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Constants;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.EventItem;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import wtf.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
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
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(Constants.eventItemName) && e.getItemDrop().getItemStack().getType().equals(Constants.eventItemMaterial)) {
                if (iep.getPlayer().hasPermission("eventitemsearch.admin")) {
                    Constants.eventItemCount += 1;
                    ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.EventItemCount", Constants.eventItemCount);
                    ItemEventSearch.getPlugin().saveConfig();

                    ItemStack is = e.getItemDrop().getItemStack();
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(Constants.eventItemName + "=" + Constants.eventItemCount);
                    is.setItemMeta(im);

                    EventItem ei = new EventItem(is);
                    ItemEventSearch.eventItems.add(ei);

                    String s = Constants.replace(Constants.msg.get("EventItemDrop"), "@NR", Constants.eventItemCount + "");
                    iep.getPlayer().sendMessage(Constants.pluginDisplayName + s);
                    e.getItemDrop().setItemStack(ei.getItemStack());
                    e.getItemDrop().setTicksLived(1);

                    for(ItemEventPlayer allIep : ItemEventSearch.players) {
                        ScoreboardUtil.update(allIep);
                    }


                } else {
                    e.setCancelled(true);
                }
            }
        }

    }

}
