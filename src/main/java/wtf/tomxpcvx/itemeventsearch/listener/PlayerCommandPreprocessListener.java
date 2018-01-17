package wtf.tomxpcvx.itemeventsearch.listener;

import wtf.tomxpcvx.itemeventsearch.Constants;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import me.rojetto.comfy.bukkit.BukkitCommandManager;
import me.rojetto.comfy.tree.CommandNode;
import me.rojetto.comfy.tree.CommandRoot;
import me.rojetto.comfy.tree.Literal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

/**
 * Created by tarik on 20.02.2017.
 */
public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onPlayerCommandPrerocess(PlayerCommandPreprocessEvent e) {
        Player   player           = e.getPlayer();
        String   completeCommand  = e.getMessage().substring(1);
        String[] completeCommands = completeCommand.split(" ");
        String   rootCommandString;
        if (completeCommands.length > 0) {
            rootCommandString = completeCommands[0];
        } else {
            player.sendMessage("Dieses Kommando ist nicht verfügbar!");
            e.setCancelled(true);
            return;
        }

        if (!rootCommandString.equalsIgnoreCase("auth")) {

            boolean exists = false;

            for (HelpTopic cmdLabel : Bukkit.getHelpMap().getHelpTopics()) {
                if (cmdLabel.getName().substring(1).equalsIgnoreCase(rootCommandString))
                    exists = true;
            }

            if (exists) {
                //Comfy check
                BukkitCommandManager bukkitCommandManager = ItemEventSearch.getPlugin().getBukkitCommandManager();
                CommandRoot          commandRoot          = bukkitCommandManager.getRoot();
                boolean              registeredByComfy    = false;
                for (CommandNode rootCommand : commandRoot.getChildren()) {
                    Literal rootCommandLiteral = (Literal) rootCommand;
                    if (rootCommandLiteral.getLabel().equalsIgnoreCase(rootCommandString)) {
                        registeredByComfy = true;
                    } else {
                        for (String alias : rootCommandLiteral.getAliases()) {
                            if (alias.equalsIgnoreCase(rootCommandString)) {
                                registeredByComfy = true;
                            }
                        }
                    }
                }

            } else {
                player.sendMessage(Constants.msg.get("CommandNotFound"));
                e.setCancelled(true);
            }
        }

    }

}
