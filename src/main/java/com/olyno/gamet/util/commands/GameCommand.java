package com.olyno.gamet.util.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class GameCommand {

    private String description;
    private String usage;
    private String pattern;
    private ArrayList<String> permissions;

    public GameCommand() {
        this.permissions = new ArrayList<>();
    }

    public abstract void execute(CommandSender sender, ArrayList<String> args);

    public void success(String message, CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "[Gamet] " + message);
    }

    public void fail(String message, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "[Gamet] " + message);
    }

    public void noPermission(CommandSender sender) {
        fail("You don't have the permission to use this command.", sender);
    }

    public void notFound(CommandSender sender) {
        fail("This command doesn't exist.", sender);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern.toLowerCase();
    }

    public String getPattern() {
        return pattern;
    }

    public ArrayList<String> getPermission() {
        return permissions;
    }

    public void addPermission(String permission) {
        this.permissions.add("gamet." + permission);
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

}