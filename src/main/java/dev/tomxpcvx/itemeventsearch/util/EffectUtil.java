package dev.tomxpcvx.itemeventsearch.util;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class EffectUtil {

    public static void playEffect(Location loc, Effect e) {
        World world = loc.getWorld();
        world.playEffect(loc, e, 5);
    }

}
