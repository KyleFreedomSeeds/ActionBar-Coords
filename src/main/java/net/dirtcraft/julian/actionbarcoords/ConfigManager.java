package net.dirtcraft.julian.actionbarcoords;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static Path dir, config;
    private static CommentedConfigurationNode confNode;
    private static ConfigurationLoader<CommentedConfigurationNode> confLoad;
    private static final String CFG = "ActionBar-Coords.conf";

    public static void setup(Path folder) {
        dir = folder;
        config = dir.resolve(CFG);
    }

    public static void load() {
        try {
            if(!Files.exists(dir)) {
                Files.createDirectory(dir);

                confLoad = HoconConfigurationLoader.builder().setPath(config).build();
                confNode = confLoad.load();
                addValues();
                save();
            } else {
                confLoad = HoconConfigurationLoader.builder().setPath(config).build();
                confNode = confLoad.load();
                save();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            confLoad.save(confNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void addValues() {
//
        confNode.getNode("Action-Bar", "message")
                .setValue("&aX: &6{x} &aY: &6{y} &aZ: &6{z}")
                .setComment("Set action bar message which will appear for the player. Available placeholders: {x}, {y}, {z}");
        confNode.getNode("Action-Bar", "enabled-by-default")
                .setValue("true")
                .setComment("Should ActionBar Coords be enabled for players by default? True or False");
//
        confNode.getNode("Toggle-Menu", "title")
                .setValue("&6ActionBar Coords&8: {toggle}")
                .setComment("Insert toggle menu title here");
        confNode.getNode("Toggle-Menu", "message")
                .setValue("&7Hello &b{playername}&7\n&7ActionBar Coords are currently {toggle}&7, click me to toggle!")
                .setComment("Insert toggle menu message here");
        confNode.getNode("Toggle-Menu", "hover")
                .setValue("&7ActionBar Coords are currently {toggle}")
                .setComment("Insert hover message here!");
        confNode.getNode("Toggle-Menu", "enabled")
                .setValue("&aenabled")
                .setComment("Insert enabled message for {toggle} here!");
        confNode.getNode("Toggle-Menu", "disabled")
                .setValue("&cdisabled")
                .setComment("Insert disabled message for {toggle} here!");
//
        confNode.getNode("Pagination", "padding")
                .setValue("&8&m-");
//
        confNode.getNode("LuckPerms", "server-context")
                .setValue("global")
                .setComment("This is useful for LuckPerms network setups, but ''global'' will work fine.");
    }

    public static CommentedConfigurationNode getConfNode(Object... node) {
        return confNode.getNode(node);
    }
}
