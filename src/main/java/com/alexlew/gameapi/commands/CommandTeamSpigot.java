package com.alexlew.gameapi.commands;

import com.alexlew.gameapi.events.TeamCreated;
import com.alexlew.gameapi.events.TeamDeleted;
import com.alexlew.gameapi.types.Game;
import com.alexlew.gameapi.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class CommandTeamSpigot implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args ) {
        Pattern creators = Pattern.compile("(add|create)");
        Pattern deleters = Pattern.compile("(remove|delete)");
        Pattern removers = Pattern.compile("from");
        Pattern adders = Pattern.compile("(to|in|for)");
        Pattern game = Pattern.compile("(in|from|of)");
        Pattern definers = Pattern.compile("(set|define)");

        if (cmd.getName().equalsIgnoreCase("team")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    Bukkit.dispatchCommand(sender, "game help 3");
                    return true;
                } else if (creators.matcher(args[0].toLowerCase()).find()) {
                    if (args[1] != null) {
                        if (adders.matcher(args[2]).find()) {
                            if (args[3] != null) {
                                if (args.length > 4) {
                                    sender.sendMessage(ChatColor.RED + "[GameAPI] The name of a team can't contain spaces in its name.");
                                } else if (sender.hasPermission("team.create")) {
                                    if (Game.games.containsKey(args[3])) {
                                        if (!Game.games.get(args[3]).teamExists(args[2])) {
                                            Team team = new Team(args[1]);
                                            Game.games.get(args[3]).addTeam(team);
                                            new TeamCreated(team);
                                            sender.sendMessage(ChatColor.GREEN + "[GameAPI] The team " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " in the game " + ChatColor.BLUE + args[3] + ChatColor.GREEN + " has been created!");
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "[GameAPI] A team with the name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " already exist in the game " + ChatColor.BLUE + args[3]);
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.RED + "[GameAPI] The game " + ChatColor.BLUE + args[3] + ChatColor.RED + " doesn't exist.");
                                    }
                                } else {
                                    noPermission(sender);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[GameAPI] This command doesn't exist.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "[GameAPI] The name of a team can't be empty.");
                    }

                } else if (deleters.matcher(args[0].toLowerCase()).find()) {
                    if (args[1] != null) {
                        if (removers.matcher(args[2]).find()) {
                            if (args[3] != null) {
                                if (args.length > 4) {
                                    sender.sendMessage(ChatColor.RED + "[GameAPI] The name of a team can't contain spaces in its name.");
                                } else if (sender.hasPermission("team.delete")) {
                                    if (Game.games.containsKey(args[3])) {
                                        if (Game.games.get(args[3]).teamExists(args[2])) {
                                            Team team = Game.games.get(args[3]).getTeam(args[2]);
                                            Game.games.get(args[3]).removeTeam(args[1]);
                                            new TeamDeleted(team);
                                            sender.sendMessage(ChatColor.GREEN + "[GameAPI] The team " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " in the game " + ChatColor.BLUE + args[3] + ChatColor.GREEN + " has been deleted!");
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "[GameAPI] A team with the name " + ChatColor.YELLOW + args[1] + ChatColor.RED + " already exist in the game " + ChatColor.BLUE + args[3]);
                                        }
                                    } else {
                                        sender.sendMessage(ChatColor.RED + "[GameAPI] The game " + ChatColor.BLUE + args[3] + ChatColor.RED + " doesn't exist.");
                                    }
                                } else {
                                    noPermission(sender);
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[GameAPI] This command doesn't exist.");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "[GameAPI] The name of a team can't be empty.");
                    }
                    return true;

                } else if (args[0] != null) {
                    if (args[2] != null) {
                        if (game.matcher(args[1].toLowerCase()).find()) {
                            if (Game.games.containsKey(args[2])) {
                                if (Game.games.get(args[2]).teamExists(args[0])) {
                                    if (args[3].equalsIgnoreCase("join")) {
                                        if (sender instanceof Player) {
                                            if (sender.hasPermission("team.join")) {
                                                Game.games.get(args[2]).getTeam(args[0]).addPlayer((Player) sender);
                                                sender.sendMessage(ChatColor.GREEN + "[GameAPI] You joined the team " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " of the game " + ChatColor.BLUE + args[2] + ChatColor.GREEN + "!");
                                            } else {
                                                noPermission(sender);
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "[GameAPI] Only players can join a team!");
                                        }
                                    } else if (args[3].equalsIgnoreCase("leave")) {
                                        if (sender instanceof Player) {
                                            if (sender.hasPermission("team.leave")) {
                                                Game.games.get(args[2]).getTeam(args[0]).removePlayer((Player) sender);
                                                sender.sendMessage(ChatColor.GREEN + "[GameAPI] You left the team " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " of the game " + ChatColor.BLUE + args[2] + ChatColor.GREEN + "!");
                                            } else {
                                                noPermission(sender);
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "[GameAPI] Only players can join a team!");
                                        }
                                    } else if (definers.matcher(args[3].toLowerCase()).find()) {
                                        if (args[4].equalsIgnoreCase("spawn")) {
                                            if (sender instanceof Player) {
                                                if (sender.hasPermission("team.set.spawn")) {
                                                    Game.games.get(args[2]).getTeam(args[0]).setSpawn(((Player) sender).getLocation());
                                                    sender.sendMessage(ChatColor.GREEN + "[GameAPI] Spawn of team " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " in the game " + ChatColor.BLUE + args[2] + ChatColor.GREEN + " has been set in " + ChatColor.DARK_AQUA + ((Player) sender).getLocation().getX() + ", " + ((Player) sender).getLocation().getY() + " and " + ((Player) sender).getLocation().getZ());
                                                } else {
                                                    noPermission(sender);
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.RED + "[GameAPI] Only players can use this command.");
                                            }
                                        } else if (args[4].equalsIgnoreCase("lobby")) {
                                            if (sender instanceof Player) {
                                                if (sender.hasPermission("team.set.lobby")) {
                                                    Game.games.get(args[2]).getTeam(args[0]).setLobby(((Player) sender).getLocation());
                                                    sender.sendMessage(ChatColor.GREEN + "[GameAPI] Lobby of team " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + " in the game " + ChatColor.BLUE + args[2] + ChatColor.GREEN + " has been set in " + ChatColor.DARK_AQUA + ((Player) sender).getLocation().getX() + ", " + ((Player) sender).getLocation().getY() + " and " + ((Player) sender).getLocation().getZ());
                                                } else {
                                                    noPermission(sender);
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.RED + "[GameAPI] Only players can use this command.");
                                            }
                                        } else if (Pattern.compile("min(imum)?").matcher(args[4].toLowerCase()).find()) {
                                            if (Pattern.compile("player(s)?").matcher(args[5].toLowerCase()).find()) {
                                                if (Pattern.compile("[0-9]{0,}").matcher(args[6]).find()) {
                                                    if (sender.hasPermission("team.set.players.minimum")) {
                                                        Game.games.get(args[2]).getTeam(args[0]).setMinPlayer(Integer.parseInt(args[6]));
                                                        sender.sendMessage(ChatColor.GREEN + "[GameAPI] The minimum player has been set to " + ChatColor.DARK_PURPLE + args[4] + " for the team " + ChatColor.YELLOW + args[0] + " in the game " + ChatColor.BLUE + args[2]);
                                                    } else {
                                                        noPermission(sender);
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.RED + "[GameAPI] " + ChatColor.YELLOW + args[4] + ChatColor.RED + " is not a integer. You can only set the minimum of player with an integer.");
                                                }
                                                return true;
                                            } else {
                                                sender.sendMessage(ChatColor.RED + "[GameAPI] The argument " + ChatColor.DARK_GREEN + args[5] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                                            }
                                        } else if (Pattern.compile("max(imum)?").matcher(args[4].toLowerCase()).find()) {
                                            if (Pattern.compile("player(s)?").matcher(args[5].toLowerCase()).find()) {
                                                if (Pattern.compile("[0-9]{0,}").matcher(args[6]).find()) {
                                                    if (sender.hasPermission("team.set.players.maximum")) {
                                                        Game.games.get(args[2]).getTeam(args[0]).setMaxPlayer(Integer.parseInt(args[6]));
                                                        sender.sendMessage(ChatColor.GREEN + "[GameAPI] The maximum player has been set to " + ChatColor.DARK_PURPLE + args[6] + " for the team " + ChatColor.YELLOW + args[0] + " in the game " + ChatColor.BLUE + args[2]);
                                                    } else {
                                                        noPermission(sender);
                                                    }
                                                } else {
                                                    sender.sendMessage(ChatColor.RED + "[GameAPI] " + ChatColor.DARK_PURPLE + args[6] + ChatColor.RED + " is not a integer. You can only set the maximum of player with an integer.");
                                                }
                                            } else {
                                                sender.sendMessage(ChatColor.RED + "[GameAPI] The argument " + ChatColor.DARK_GREEN + args[5] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                                            }
                                        } else {
                                            sender.sendMessage(ChatColor.RED + "[GameAPI] The argument " + ChatColor.DARK_GREEN + args[4] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                                        }
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "[GameAPI] The team " + ChatColor.YELLOW + args[0] + ChatColor.RED + " doesn't exist.");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[GameAPI] The game " + ChatColor.BLUE + args[2] + ChatColor.RED + " doesn't exist.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[GameAPI] You used a wrong syntax. Please check the help command.");
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private void noPermission( CommandSender sender ) {
        sender.sendMessage(ChatColor.RED + "[GameAPI] You don't have the permission to use this command.");
    }
}
