package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {

        ItemEventPlayer iep = ItemEventSearch.getItemEventPlayerByName(e.getPlayer().getName());

        e.setFormat(ChatColor.YELLOW + "[" + iep.getLocatedEventItemCount() + "] " + ChatColor.RESET + e.getFormat() + e.getMessage());


            /*if(Variablen.Cookies.get(p) <= 10){
                String keksanzahl = ChatColor.YELLOW + "[" + Variablen.Cookies.get(p) + "] ";
                e.setFormat("" + keksanzahl + ChatColor.AQUA + "[Anfänger] " + ChatColor.RESET + "§3%1$s: §b%2$s");
            } else if (Variablen.Cookies.get(p) <= 20){
                String keksanzahl = ChatColor.YELLOW + "[" + Variablen.Cookies.get(p) + "] ";
                e.setFormat("" + keksanzahl + ChatColor.AQUA + "[Keksjäger] " + ChatColor.RESET + "§3%1$s: §b%2$s");
            } else if(Variablen.Cookies.get(p) <= 40){
                String keksanzahl = ChatColor.YELLOW + "[" + Variablen.Cookies.get(p) + "] ";
                e.setFormat("" + keksanzahl + ChatColor.AQUA + "[Keksdetektiv] " + ChatColor.RESET + "§3%1$s: §b%2$s");
            } else if(Variablen.Cookies.get(p) <= 56){
                String keksanzahl = ChatColor.YELLOW + "[" + Variablen.Cookies.get(p) + "] ";
                e.setFormat("" + keksanzahl + ChatColor.AQUA + "[Keksmeister] " + ChatColor.RESET + "§3%1$s: §b%2$s");
            } else if(Variablen.Cookies.get(p) == 57){
                String keksanzahl = ChatColor.YELLOW + "[" + Variablen.Cookies.get(p) + "] ";
                e.setFormat("" + keksanzahl + ChatColor.AQUA + "[Auserwälter] " + ChatColor.RESET + "§3%1$s: §b%2$s");
            }*/

    }

}
