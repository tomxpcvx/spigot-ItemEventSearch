package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Constants;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.EventItem;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import wtf.tomxpcvx.itemeventsearch.util.ScoreboardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        ItemEventPlayer iep = ItemEventSearch.getItemEventPlayerByName(e.getPlayer().getName());

        if (iep != null) {
            if (e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
                if (e.getItem().getItemStack().getItemMeta().getDisplayName().startsWith(Constants.eventItemName)) {
                    e.setCancelled(true);
                    EventItem currentItem = new EventItem(e.getItem().getItemStack());

                    if (iep.isEventItemRemoveMode()) {
                        int index = -1;
                        ArrayList<Integer> eventIds = new ArrayList<>();
                        for (EventItem eventItem : ItemEventSearch.eventItems) {
                            index++;
                            if (eventItem.getEventItemId() == currentItem.getEventItemId()) {
                                System.out.println("YES");
                                currentItem.setMarkedRemoval(true);
                                if (currentItem.isMarkedRemoval()) {
                                    e.setCancelled(false);
                                    eventIds.add(index);
                                    Constants.eventItemCount = Constants.eventItemCount - 1;
                                    iep.getPlayer().sendMessage(Constants.pluginDisplayName + Constants.replace(Constants.replace(Constants.msg.get("RemovedEventItem"), "@NR", String.valueOf(eventItem.getEventItemId())), "@NR", String.valueOf(eventItem.getMaterial().name())));
                                    for (ItemEventPlayer itemEventPlayer : ItemEventSearch.players) {
                                        itemEventPlayer.removeLocatedEventItemId(currentItem.getEventItemId());
                                        ScoreboardUtil.update(itemEventPlayer);
                                    }
                                }
                            }
                        }
                        for(int id : eventIds) {
                            ItemEventSearch.eventItems.remove(id);
                        }
                    } else if (iep.getPlayer().hasPermission("itemeventsearch.play")) {
                        for (EventItem eventItem : ItemEventSearch.eventItems) {
                            if (eventItem.getEventItemId() == currentItem.getEventItemId()) {

                                boolean playerHasItem = false;
                                for (int eventItemId : iep.getLocatedEventItemIds()) {
                                    if (eventItemId == currentItem.getEventItemId()) {
                                        playerHasItem = true;
                                    }
                                }

                                if (!playerHasItem) {
                                    iep.addLocatedEventItemId(currentItem.getEventItemId());
                                    currentItem.itemPickupSequenz(iep);
                                    ScoreboardUtil.update(iep);
                                } else {
                                    if(iep.getLastAlreadyMessageDelay() == 50) {
                                        iep.getPlayer().sendMessage(Constants.pluginDisplayName + Constants.msg.get("Already"));
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
            ItemEventSearch.setItemEventPlayer(iep);
        }
    }
}
