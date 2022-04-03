package dev.tomxpcvx.itemeventsearch.util;

import me.rojetto.comfy.argument.BooleanType;
import me.rojetto.comfy.argument.FloatType;
import me.rojetto.comfy.bukkit.BukkitCommandManager;
import me.rojetto.comfy.tree.Argument;
import me.rojetto.comfy.tree.Literal;
import dev.tomxpcvx.itemeventsearch.command.CommandItemEventSearch;

public class CommandUtil {

    public static void registerCommands(BukkitCommandManager bukkitCommandManager) {
        registerCommandListeners(bukkitCommandManager);

        registerItemEventSearchCommand(bukkitCommandManager);

        bukkitCommandManager.registerCommands();
    }

    private static void registerCommandListeners(BukkitCommandManager bukkitCommandManager) {
        bukkitCommandManager.addListener(new CommandItemEventSearch());
    }

    private static void registerItemEventSearchCommand(BukkitCommandManager bukkitCommandManager) {
        bukkitCommandManager.addCommand(new Literal("itemeventsearch", "ies", "itemevent")
                .then(new Literal("give", "g")
                        .description("Gives the player the event item")
                        .executes("itemeventsearch.command.give")
                        .permission("itemeventsearch.admin"))
                .then(new Literal("world", "w")
                        .description("Set the event world")
                        .executes("itemeventsearch.command.world")
                        .permission("itemeventsearch.admin"))
                .then(new Literal("prize", "p")
                        .then(new Argument("prize", new FloatType())
                                .description("Set the winning prize")
                                .executes("itemeventsearch.command.prize"))
                        .permission("itemeventsearch.admin"))
                .then(new Literal("winners", "w")
                        .description("Get a list of all winners")
                        .executes("itemeventsearch.command.winners")
                        .permission("itemeventsearch.admin"))
                .then(new Literal("remove", "r", "rm")
                        .then(new Argument("state", new BooleanType(new String[]{"true", "on", "yes", "enabled", "wahr", "ja", "aktiviert", "an"}, new String[]{"false", "off", "no", "disabled", "falsch", "nein", "deaktiviert", "aus"}))
                                .description("Set the remove mode")
                                .executes("itemeventsearch.command.remove"))
                        .permission("itemeventsearch.admin")));
    }

}
