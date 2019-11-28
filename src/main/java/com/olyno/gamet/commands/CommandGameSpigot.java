package com.olyno.gamet.commands;

import com.olyno.gamet.Gamet;
import com.olyno.gamet.events.GameCreated;
import com.olyno.gamet.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class CommandGameSpigot implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args ) {
        Pattern creators = Pattern.compile("(add|create)");
        Pattern deleters = Pattern.compile("(remove|delete)");
        Pattern definers = Pattern.compile("(set|define)");

        if (cmd.getName().equalsIgnoreCase("game")) {
            if (args.length == 0) {
                Bukkit.dispatchCommand(sender, "game help");
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                if (args.length == 1 || args[1].equalsIgnoreCase("0") || args[1].equalsIgnoreCase("1")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (1/3) ===============\n\n"
                            + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Global:\n\n"
                            + ChatColor.GOLD + "/game help" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Show all commands of Gamet.\n"
                            + ChatColor.GOLD + "/game help <number>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Show a page of help.\n\n"
                            + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Information:\n\n"
                            + ChatColor.GOLD + "Current Version: " + ChatColor.WHITE + Gamet.getPlugin(Gamet.class).getDescription().getVersion() + "\n"
                            + ChatColor.GOLD + "Author: " + ChatColor.WHITE + Gamet.getPlugin(Gamet.class).getDescription().getAuthors().toArray()[0] + "\n"
                    );
                } else if (args[1].equalsIgnoreCase("2")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (2/3) ===============\n\n"
                            + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Games:\n\n"
                            + ChatColor.GOLD + "/game create <name>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Create a game with name <name>.\n"
                            + ChatColor.GOLD + "/game delete <name>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Delete a game with name <name>.\n"
                            + ChatColor.GOLD + "/game start <name>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Start a game manually.\n"
                            + ChatColor.GOLD + "/game stop <name>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Stop a game manually.\n"
                            + ChatColor.GOLD + "/game <name> set minimum player <amount>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the minimum number of players for game <name> to <amount>. It's the amount of player before starting the game.\n"
                            + ChatColor.GOLD + "/game <name> set maximum player <amount>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the maximum number of players for game <name> to <amount>. It's the amount of player before starting the game.\n"
                            + ChatColor.GOLD + "/game <name> set maximum points <amount>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the maximum points of the game <name> to <amount>. It's the amount of points to score to win the game.\n"
                            + ChatColor.GOLD + "/game <name> set lobby" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the lobby of the game <name> where you are. It's where players join the game.\n"
                            + ChatColor.GOLD + "/game <name> set spawn" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the spawn of the game <name> where you are. It's where players spawn when they join the game.\n"
                    );
                } else if (args[1].equalsIgnoreCase("3")) {
                    sender.sendMessage(ChatColor.DARK_AQUA + "=============== Gamet - Help (3/3) ===============\n\n"
                            + ChatColor.DARK_AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Teams:\n\n"
                            + ChatColor.GOLD + "/team create <team> in <game>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Create a team with name <team> in the game <game>.\n"
                            + ChatColor.GOLD + "/team delete <team> in <game>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Delete a team with name <team> in the game <game>.\n"
                            + ChatColor.GOLD + "/team join <team> in <game>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Join the team <team> of the game <game>.\n"
                            + ChatColor.GOLD + "/team leave <team> in <game>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Leave the team <team> of the game <game>.\n"
                            + ChatColor.GOLD + "/team <team> set minimum player <amount>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the minimum number of players for team <team> to <amount>.\n"
                            + ChatColor.GOLD + "/team <team> set maximum player <amount>" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the maximum number of players for team <team> to <amount>.\n"
                            + ChatColor.GOLD + "/team <team> set spawn" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the spawn of the team <team> of the game <game> where you are. It's where the team spawns when the game starts.\n"
                            + ChatColor.GOLD + "/team <team> set lobby" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the lobby of the team <team> of the game <game> where you are. It's where players spawn when he joins the team.\n"
                    );
                } else if (Integer.parseInt(args[1]) > 3) {
                    Bukkit.dispatchCommand(sender, "game help");
                }
                return true;

            } else if (creators.matcher(args[0].toLowerCase()).find()) {
                if (args.length > 2) {
                    sender.sendMessage(ChatColor.RED + "[Gamet] The name of a game can't contain spaces in its name.");
                } else if (sender.hasPermission("game.create")) {
                    Game game = new Game(args[1]);
					Game.getGames().put(args[1], game);
                    new GameCreated(game);
                    sender.sendMessage(ChatColor.GREEN + "[Gamet] The game " + ChatColor.BLUE + args[1] + ChatColor.GREEN + " has been created!");
                } else {
                    noPermission(sender);
                }
                return true;

            } else if (deleters.matcher(args[0].toLowerCase()).find()) {
                if (args.length > 2) {
                    sender.sendMessage(ChatColor.RED + "[Gamet] The name of a game can't contain spaces in its name.");
                } else if (sender.hasPermission("game.delete")) {
					Game game = Game.getGames().get(args[1]);
					Game.getGames().remove(args[1]);
                    new GameCreated(game);
                    sender.sendMessage(ChatColor.GREEN + "[Gamet] The game " + ChatColor.BLUE + args[1] + ChatColor.GREEN + " has been deleted!");
                } else {
                    noPermission(sender);
                }
                return true;

			} else if (Game.getGames().containsKey(args[0])) {
                if (args[1].equalsIgnoreCase("join")) {
					Game.getGames().get(args[0]).addPlayer((Player) sender);
                } else if (definers.matcher(args[1].toLowerCase()).find()) {
                    if (args[2].equalsIgnoreCase("lobby")) {
                        if (sender instanceof Player) {
                            if (sender.hasPermission("game.set.lobby")) {
								Game.getGames().get(args[0]).setLobby(((Player) sender).getLocation());
                                sender.sendMessage(ChatColor.GREEN + "[Gamet] Lobby of game " + ChatColor.BLUE + args[0] + ChatColor.GREEN + " has been set in " + ChatColor.DARK_AQUA + ((Player) sender).getLocation().getX() + ", " + ((Player) sender).getLocation().getY() + " and " + ((Player) sender).getLocation().getZ());
                            } else {
                                noPermission(sender);
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Gamet] Only players can use this command.");
                        }
                    } else if (args[2].equalsIgnoreCase("spawn")) {
                        if (sender instanceof Player) {
                            if (sender.hasPermission("game.set.spawn")) {
								Game.getGames().get(args[0]).setSpawn(((Player) sender).getLocation());
								sender.sendMessage(ChatColor.GREEN + "[Gamet] Spawn of game " + ChatColor.BLUE + args[0] + ChatColor.GREEN + " has been set in " + ChatColor.DARK_AQUA + ((Player) sender).getLocation().getX() + ", " + ((Player) sender).getLocation().getY() + " and " + ((Player) sender).getLocation().getZ());
                            } else {
                                noPermission(sender);
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Gamet] Only players can use this command.");
                        }
                    } else if (Pattern.compile("min(imum)?").matcher(args[2].toLowerCase()).find()) {
                        if (Pattern.compile("player(s)?").matcher(args[3].toLowerCase()).find()) {
                            if (Pattern.compile("[0-9]{0,}").matcher(args[4]).find()) {
                                if (sender.hasPermission("game.set.players.minimum")) {
									Game.getGames().get(args[0]).setMinPlayer(Integer.parseInt(args[4]));
                                    sender.sendMessage(ChatColor.GREEN + "[Gamet] The minimum player has been set to " + ChatColor.DARK_PURPLE + args[4] + " for the game " + ChatColor.BLUE + args[0]);
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[Gamet] " + ChatColor.YELLOW + args[4] + ChatColor.RED + " is not a integer. You can only set the minimum of player with an integer.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Gamet] The argument " + ChatColor.DARK_GREEN + args[3] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                        }
                    } else if (Pattern.compile("max(imum)?").matcher(args[2].toLowerCase()).find()) {
                        if (Pattern.compile("player(s)?").matcher(args[3].toLowerCase()).find()) {
                            if (Pattern.compile("[0-9]{0,}").matcher(args[4]).find()) {
                                if (sender.hasPermission("game.set.players.maximum")) {
									Game.getGames().get(args[0]).setMaxPlayer(Integer.parseInt(args[4]));
                                    sender.sendMessage(ChatColor.GREEN + "[Gamet] The maximum player has been set to " + ChatColor.DARK_PURPLE + args[4] + " for the game " + ChatColor.BLUE + args[0]);
                                } else {
                                    noPermission(sender);
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[Gamet] " + ChatColor.YELLOW + args[4] + ChatColor.RED + " is not a integer. You can only set the maximum of player with an integer.");
                            }
                        } else if (Pattern.compile("point(s)?").matcher(args[3].toLowerCase()).find()) {
                            if (Pattern.compile("[0-9]{0,}").matcher(args[4]).find()) {
                                if (sender.hasPermission("game.set.points.maximum")) {
									Game.getGames().get(args[0]).setMaxPlayer(Integer.parseInt(args[4]));
                                    sender.sendMessage(ChatColor.GREEN + "[Gamet] The maximum points has been set to " + ChatColor.DARK_PURPLE + args[4] + " for the game " + ChatColor.BLUE + args[0]);
                                } else {
                                    noPermission(sender);
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "[Gamet] " + ChatColor.YELLOW + args[4] + ChatColor.RED + " is not a integer. You can only set the maximum of points with an integer.");
                            }
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "[Gamet] The argument " + ChatColor.DARK_GREEN + args[3] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "[Gamet] The argument " + ChatColor.DARK_GREEN + args[2] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[Gamet] The argument " + ChatColor.DARK_GREEN + args[1] + ChatColor.RED + " doesn't exist. Make \"/team help\" for more informations.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "[Gamet] The game " + args[0] + " doesn't exist.");
            }
            return true;
        } else {
            return false;
        }
    }

    private void noPermission( CommandSender sender ) {
        sender.sendMessage(ChatColor.RED + "[Gamet] You don't have the permission to use this command.");
    }
}
