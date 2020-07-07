package wtf.tomxpcvx.itemeventsearch.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;

public class ItemDespawnListener implements Listener {

    @EventHandler
    public void onDespawn(ItemDespawnEvent e) {
        if(e.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
            if(e.getEntity().getItemStack().getItemMeta().getDisplayName().startsWith(ItemEventSearchUtil.eventItemName)) {
                e.setCancelled(true);
                e.getEntity().setTicksLived(999999999);
            }
        }
    }

}
