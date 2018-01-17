package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class ItemDespawnListener implements Listener {

    @EventHandler
    public void onDespawn(ItemDespawnEvent e) {
        if(e.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
            if(e.getEntity().getItemStack().getItemMeta().getDisplayName().startsWith(Constants.eventItemName)) {
                e.setCancelled(true);
                e.getEntity().setTicksLived(99999999);
            }
        }
    }

}
