package wtf.tomxpcvx.itemeventsearch;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Constants {

    public static int eventItemCount;
    public static String eventItemName;
    public static Material eventItemMaterial;
    public static double prize;
    public static String pluginName = "ItemEventSearch";
    public static String pluginDisplayName;
    public static String pluginConsoleName = "[" + pluginName + "] ";
    public static HashMap<String, String> msg = new HashMap<String, String>();

    public static String replace(String txt, String arg, String replaced) {
        return txt.replace(arg, replaced);
    }
}
