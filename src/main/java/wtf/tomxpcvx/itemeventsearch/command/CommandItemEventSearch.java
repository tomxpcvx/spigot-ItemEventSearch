package wtf.tomxpcvx.itemeventsearch.command;

import wtf.tomxpcvx.itemeventsearch.ItemEventSearch;
import wtf.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;
import me.rojetto.comfy.CommandListener;
import me.rojetto.comfy.annotation.Arg;
import me.rojetto.comfy.annotation.CommandHandler;
import me.rojetto.comfy.bukkit.BukkitCommandContext;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wtf.tomxpcvx.itemeventsearch.util.ItemEventSearchUtil;
import wtf.tomxpcvx.itemeventsearch.util.PluginUtil;

import java.util.List;
import java.util.UUID;

public class CommandItemEventSearch implements CommandListener {

    @CommandHandler("itemeventsearch.command.give")
    public void onGiveCommand(BukkitCommandContext ctxt) {
        if(ctxt.getSender().isPlayer()) {

            Player player = (Player) ctxt.getSender().getSender();
            World w = Bukkit.getWorld(ItemEventSearch.getPlugin().getConfig().getString("ItemEventSearch.World"));
            if(player.getWorld().equals(w)) {
                ItemStack is = new ItemStack(ItemEventSearchUtil.eventItemMaterial);
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ItemEventSearchUtil.eventItemName);
                is.setItemMeta(im);
                is.setAmount(64);
                player.getInventory().addItem(is);
                player.sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandGiveEventItem"));
            } else {
                player.sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandGiveWrongWorld"));
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
            player.sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandWorldSet").replace("@WORLD", world.getName()));
        }
    }

    @CommandHandler("itemeventsearch.command.prize")
    public void onPrizeCommand(BukkitCommandContext ctxt, @Arg("prize") float prize) {
        if(ctxt.getSender().isPlayer()) {
            Player player = (Player) ctxt.getSender().getSender();
            ItemEventSearch.getPlugin().getConfig().set("ItemEventSearch.Prize", prize);
            ItemEventSearch.getPlugin().saveConfig();
            player.sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandPrizeSet").replace("@MONEY", String.valueOf(prize)));
        }
    }

    @CommandHandler("itemeventsearch.command.winners")
    public void onWinnersCommand(BukkitCommandContext ctxt) {
        if(ctxt.getSender().isPlayer()) {
            Player player = (Player) ctxt.getSender().getSender();
            List<String> playerUuids = (List<String>) ItemEventSearch.getPlugin().getConfig().getList("ItemEventSearch.Winners");
            player.sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandWinners"));
            for(String playerUuid : playerUuids) {
                player.sendMessage(ItemEventSearchUtil.messages.get("CommandWinnersPrefixPlayerNames") + Bukkit.getOfflinePlayer(UUID.fromString(playerUuid)).getName());
            }
        }
    }

    @CommandHandler("itemeventsearch.command.remove")
    public void onRemoveCommand(BukkitCommandContext ctxt, @Arg("state") boolean enabled) {
        if(ctxt.getSender().isPlayer()) {
            ItemEventPlayer iep = ItemEventSearchUtil.getItemEventPlayerByName(ctxt.getSender().getSender().getName());
            if(enabled) {
                iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandRemoveEnabled"));
                iep.setEventItemRemoveMode(true);
            } else {
                iep.getPlayer().sendMessage(PluginUtil.pluginDisplayName + ItemEventSearchUtil.messages.get("CommandRemoveDisabled"));
                iep.setEventItemRemoveMode(false);
            }

        }
    }

}
