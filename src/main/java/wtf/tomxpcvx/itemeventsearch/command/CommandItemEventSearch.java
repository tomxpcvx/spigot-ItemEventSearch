package wtf.tomxpcvx.itemeventsearch.command;

import wtf.tomxpcvx.itemeventsearch.Constants;
import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventPlayer;
import me.rojetto.comfy.CommandListener;
import me.rojetto.comfy.annotation.Arg;
import me.rojetto.comfy.annotation.CommandHandler;
import me.rojetto.comfy.bukkit.BukkitCommandContext;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CommandItemEventSearch implements CommandListener {

    @CommandHandler("itemeventsearch.command.give")
    public void onGiveCommand(BukkitCommandContext ctxt) {
        if(ctxt.getSender().isPlayer()) {

            Player player = (Player) ctxt.getSender().getSender();
            World w = Bukkit.getWorld(ItemEventSearch.getPlugin().getConfig().getString("ItemEventSearch.World"));
            if (player.getWorld().equals(w)) {
                ItemStack is = new ItemStack(Constants.eventItemMaterial);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(Constants.eventItemName);
                is.setItemMeta(im);
                is.setAmount(64);
                player.getInventory().addItem(is);
                player.sendMessage(Constants.pluginDisplayName + Constants.msg.get("CommandGiveEventItem"));
            } else {
                player.sendMessage(Constants.pluginDisplayName + Constants.msg.get("CommandGiveWrongWorld"));
            }
        }
    }

    @CommandHandler("itemeventsearch.command.world")
    public void onWorldCommand(BukkitCommandContext ctxt) {
        if(ctxt.getSender().isPlayer()) {
            Player player = (Player) ctxt.getSender().getSender();
            World world = player.getWorld();
            ItemEventSearch.getPlugin().getConfig();
            ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.World", world.getName());
            ItemEventSearch.getPlugin().saveConfig();
            player.sendMessage(Constants.pluginDisplayName + Constants.replace(Constants.msg.get("CommandWorldSet"), "@WORLD", world.getName()));
        }
    }

    @CommandHandler("itemeventsearch.command.prize")
    public void onPrizeCommand(BukkitCommandContext ctxt, @Arg("prize") float prize) {
        if(ctxt.getSender().isPlayer()) {
            Player player = (Player) ctxt.getSender().getSender();
            ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.Prize", prize);
            ItemEventSearch.getPlugin().saveConfig();
            player.sendMessage(Constants.pluginDisplayName + Constants.replace(Constants.msg.get("CommandPrizeSet"), "@MONEY", String.valueOf(prize)));
        }
    }

    @CommandHandler("itemeventsearch.command.winners")
    public void onWinnersCommand(BukkitCommandContext ctxt) {
        if(ctxt.getSender().isPlayer()) {
            Player player = (Player) ctxt.getSender().getSender();
            List<String> playerNames = (List<String>) ItemEventSearch.getPlugin().getConfig().getList("ItemEventSearch.Winners");
            player.sendMessage(Constants.pluginDisplayName + Constants.msg.get("CommandWinners"));
            for(String playerName : playerNames) {
                player.sendMessage(Constants.msg.get("CommandWinnersPrefixPlayerNames") + playerName);
            }
        }
    }

    @CommandHandler("itemeventsearch.command.remove")
    public void onRemoveCommand(BukkitCommandContext ctxt, @Arg("state") boolean enabled) {
        if(ctxt.getSender().isPlayer()) {
            ItemEventPlayer iep = ItemEventSearch.getItemEventPlayerByName(ctxt.getSender().getSender().getName());
            if(enabled) {
                iep.getPlayer().sendMessage(Constants.pluginDisplayName + Constants.msg.get("CommandRemoveEnabled"));
                iep.setEventItemRemoveMode(true);
            } else {
                iep.getPlayer().sendMessage(Constants.pluginDisplayName + Constants.msg.get("CommandRemoveDisabled"));
                iep.setEventItemRemoveMode(false);
            }

        }
    }

}
