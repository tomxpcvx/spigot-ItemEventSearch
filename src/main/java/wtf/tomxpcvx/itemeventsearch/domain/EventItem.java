package wtf.tomxpcvx.itemeventsearch.domain;

import wtf.tomxpcvx.itemeventsearch.util.EffectUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import wtf.tomxpcvx.itemeventsearch.util.PluginUtil;

import java.util.HashMap;
import java.util.Map;

public class EventItem implements ConfigurationSerializable {

    private int eventItemId;
    private Location eventItemLocation;
    private Material material;
    private ItemStack itemStack;
    private boolean markedRemoval;

    public EventItem(ItemStack itemStack) {
        this.eventItemId = Integer.parseInt(itemStack.getItemMeta().getDisplayName().replace(ItemEventSearchUtil.eventItemName + "=", ""));
        this.material = itemStack.getType();
        this.itemStack = itemStack;
    }

    public EventItem(Map<String, Object> map) {
        this.eventItemId = (int) map.get("eventItemId");
        this.material = Material.getMaterial(map.get("material").toString());
        this.itemStack = (ItemStack) map.get("itemStack");
        this.eventItemLocation = (Location) map.get("eventItemLocation");
        this.markedRemoval = (boolean) map.get("markedRemoval");
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public EventItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public int getEventItemId() {
        return this.eventItemId;
    }

    public void setEventItemId(int eventItemId) {
        this.eventItemId = eventItemId;
    }

    public Location getEventItemLocation() {
        return eventItemLocation;
    }

    public void setEventItemLocation(Location eventItemLocation) {
        this.eventItemLocation = eventItemLocation;
    }

    public boolean isMarkedRemoval() {
        return markedRemoval;
    }

    public void setMarkedRemoval(boolean markedRemoval) {
        this.markedRemoval = markedRemoval;
    }

    public void itemPickupSequence(ItemEventPlayer iep) {
        Location playerLocation = iep.getPlayer().getLocation();
        EffectUtil.playEffect(playerLocation, Effect.valueOf(ItemEventSearchUtil.messages.get("PickupEffect")));
        iep.getPlayer().playSound(playerLocation, Sound.valueOf(ItemEventSearchUtil.messages.get("PickupSound")), 10.0F, 1.0F);

        String eventItemPickup1 = ItemEventSearchUtil.messages.get("EventItemPickUp1")
            .replace("@NR", String.valueOf(iep.getLocatedEventItemCount()));
        String eventItemPickup2 = ItemEventSearchUtil.messages.get("EventItemPickUp2")
            .replace("@NR", String.valueOf(iep.getLocatedEventItemCount()))
            .replace("@MAX", String.valueOf(ItemEventSearchUtil.eventItemCount));

        iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + eventItemPickup1);
        iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + eventItemPickup2);

        if(iep.getLocatedEventItemCount() == ItemEventSearchUtil.eventItemCount) {
            iep.setWin();
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("eventItemId", this.eventItemId);
        map.put("material", this.material.name());
        map.put("itemStack", this.itemStack);
        map.put("eventItemLocation", this.eventItemLocation);
        map.put("markedRemoval", this.markedRemoval);
        return map;
    }
}
