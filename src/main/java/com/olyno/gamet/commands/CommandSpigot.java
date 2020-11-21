package com.olyno.gamet.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.olyno.gamet.util.PackageLoader;
import com.olyno.gamet.util.commands.CommandType;
import com.olyno.gamet.util.commands.GameCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSpigot implements CommandExecutor {

    public static HashMap<CommandType, ArrayList<GameCommand>> COMMANDS = new HashMap<>();

    public CommandSpigot() {
        COMMANDS.put(CommandType.GLOBAL, new ArrayList<>());
        COMMANDS.put(CommandType.GAME, new ArrayList<>());
        COMMANDS.put(CommandType.TEAM, new ArrayList<>());
        new PackageLoader<GameCommand>("com.olyno.gamet.commands.global", "register global commands").getList()
			.thenAccept(commands -> {
				for (GameCommand cmd : commands) {
                    COMMANDS.get(CommandType.GLOBAL).add(cmd);
				}
            });
        new PackageLoader<GameCommand>("com.olyno.gamet.commands.game", "register game commands").getList()
			.thenAccept(commands -> {
				for (GameCommand cmd : commands) {
                    COMMANDS.get(CommandType.GAME).add(cmd);
				}
            });
        new PackageLoader<GameCommand>("com.olyno.gamet.commands.team", "register team commands").getList()
			.thenAccept(commands -> {
				for (GameCommand cmd : commands) {
                    COMMANDS.get(CommandType.TEAM).add(cmd);
				}
			});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            final String commandName = cmd.getName().toLowerCase();
            final CommandType commandType = CommandType.valueOf(commandName.replaceAll("gamet", "global").toUpperCase());
            final List<GameCommand> commands = COMMANDS
                .get(commandType)
                .stream()
                .collect(Collectors.toList());

            if (args.length == 0) {
                Bukkit.dispatchCommand(sender, "gamet help");
                return true;
            } else if (Arrays.asList(CommandType.values()).contains(commandType)) {
                if (!commandFound(sender, commandName + " " + String.join(" ", args), commands)) {
                    sender.sendMessage(ChatColor.RED + "[Gamet] Command not found.");
                }
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private boolean commandFound(final CommandSender sender, final String executedCommand, final List<GameCommand> commands) {
        for (GameCommand command : commands) {
            final String commandPattern = String.format("^%s %s$", executedCommand.split(" ")[0], command.getPattern());
            Matcher commandMatcher = Pattern.compile(commandPattern).matcher(executedCommand);
            ArrayList<String> groups = new ArrayList<>();
            while (commandMatcher.find()) {
                groups.add(commandMatcher.group(1));
            }
            if (commandMatcher.matches()) {
                commandMatcher.reset();
                for (final String permission : command.getPermission()) {
                    if (sender.hasPermission(permission)) {
                        ArrayList<String> newArgs = new ArrayList<>(
                            groups.stream()
                                .filter(arg -> arg != null)
                                .map(arg -> arg.trim())
                                .collect(Collectors.toList())
                        );
                        command.execute(sender, newArgs);
                        return true;
                    }
                }
                command.noPermission(sender);
                return true;
            }
        }
        return false;
    }
    
}