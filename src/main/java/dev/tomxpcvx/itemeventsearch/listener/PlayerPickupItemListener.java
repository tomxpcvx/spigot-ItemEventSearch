package dev.tomxpcvx.itemeventsearch.listener;

import dev.tomxpcvx.itemeventsearch.domain.EventItem;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import dev.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import dev.tomxpcvx.itemeventsearch.util.PluginUtil;
import dev.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        ItemEventPlayer iep = ItemEventSearchUtil.getItemEventPlayerByName(e.getPlayer().getName());

        if(iep != null) {
            if(e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
                if(e.getItem().getItemStack().getItemMeta().getDisplayName().startsWith(ItemEventSearchUtil.eventItemName)) {
                    e.setCancelled(true);
                    EventItem currentItem = new EventItem(e.getItem().getItemStack());

                    if(iep.isEventItemRemoveMode()) {
                        int index = -1;
                        ArrayList<Integer> eventIds = new ArrayList<>();
                        for (EventItem eventItem : ItemEventSearchUtil.eventItems) {
                            index++;
                            if(eventItem.getEventItemId() == currentItem.getEventItemId()) {
                                currentItem.setMarkedRemoval(true);
                                if(currentItem.isMarkedRemoval()) {
                                    e.setCancelled(false);
                                    eventIds.add(index);
                                    ItemEventSearchUtil.eventItemCount = ItemEventSearchUtil.eventItemCount - 1;
                                    iep.getPlayer().sendMessage(
                                            PluginUtil.pluginDisplayName +
                                                    ItemEventSearchUtil.messages.get("RemovedEventItem")
                                                            .replace("@NR", String.valueOf(eventItem.getEventItemId()))
                                                            .replace("@NR", eventItem.getMaterial().name()));
                                    for (ItemEventPlayer itemEventPlayer : ItemEventSearchUtil.players) {
                                        itemEventPlayer.removeLocatedEventItemId(currentItem.getEventItemId());
                                        ScoreboardUtil.update(itemEventPlayer);
                                    }
                                }
                            }
                        }
                        for(int id : eventIds) {
                            ItemEventSearchUtil.eventItems.remove(id);
                        }
                    } else if(iep.getPlayer().hasPermission("itemeventsearch.play")) {
                        for (EventItem eventItem : ItemEventSearchUtil.eventItems) {
                            if(eventItem.getEventItemId() == currentItem.getEventItemId()) {

                                boolean playerHasItem = false;
                                for (int eventItemId : iep.getLocatedEventItemIds()) {
                                    if (eventItemId == currentItem.getEventItemId()) {
                                        playerHasItem = true;
                                        break;
                                    }
                                }

                                if(!playerHasItem) {
                                    iep.addLocatedEventItemId(currentItem.getEventItemId());
                                    currentItem.itemPickupSequence(iep);
                                    ScoreboardUtil.update(iep);
                                } else {
                                    if(iep.getLastAlreadyMessageDelay() == 50) {
                                        iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("Already"));
                                        iep.setLastAlreadyMessageDelay(0);
                                    } else {
                                        iep.addLastAlreadyMessageDelay(1);
                                    }
                                }
                            }

                        }
                    }
                }
            }
            ItemEventSearchUtil.setItemEventPlayer(iep);
        }
    }
}
