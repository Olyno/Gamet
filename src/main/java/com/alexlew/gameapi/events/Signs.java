package com.alexlew.gameapi.events;

import com.alexlew.gameapi.GameAPI;
import com.alexlew.gameapi.types.Game;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs implements Listener {

	public Signs(GameAPI plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGameSignClicked(PlayerInteractEvent event) {
		if (GameAPI.manage_automatically) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				if (block.getType().toString().contains("SIGN")) {
					Sign sign = (Sign) block.getState();
					String game = ChatColor.stripColor(sign.getLine(0)).replaceAll("(\\[|\\]){0,}", "");
					if (sign.getLine(1).equals(ChatColor.DARK_AQUA + "Join")) {
						if (sign.getLine(2).replaceAll(" ", "").isEmpty()) {
							if (event.getPlayer().hasPermission("game.join")) {
								Game.getGames().get(game).addPlayer(event.getPlayer());
							} else {
								event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to join this game.");
							}
						} else {
							String team = ChatColor.stripColor(sign.getLine(2)).replaceAll("\\s+\\S+$", "");
							Game.getGames().get(game).getTeam(team).addPlayer(event.getPlayer());
						}
					} else if (sign.getLine(1).equals(ChatColor.DARK_AQUA + "Leave")) {
						if (sign.getLine(2).replaceAll(" ", "").isEmpty()) {
							if (event.getPlayer().hasPermission("game.leave")) {
								Game.getGames().get(game).removePlayer(event.getPlayer());
							} else {
								event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to leave this game.");
							}
						} else {
							String team = ChatColor.stripColor(sign.getLine(2)).replaceAll(" \\\\S*$", "");
							Game.getGames().get(game).getTeam(team).removePlayer(event.getPlayer());
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void onGameSignPlaced(SignChangeEvent event) {
		if (GameAPI.manage_automatically) {
			if (event.getLine(0).equalsIgnoreCase("[gameapi]")) {
				String game = event.getLine(2);
				if (!Game.getGames().containsKey(game)) {
					event.getPlayer().sendMessage(ChatColor.RED + "[GameAPI] The game " + ChatColor.BLUE + game + ChatColor.RED + " doesn't exist. Remember about create it.");
				}
				event.setLine(0, ChatColor.GOLD + "[" + ChatColor.YELLOW + game + ChatColor.GOLD + "]");
				if (event.getLine(1).equalsIgnoreCase("join")) {
					event.setLine(1, ChatColor.DARK_AQUA + "Join");
					if (event.getLine(3).isEmpty()) {
						event.setLine(2, " ");
						event.setLine(3, " ");
					} else {
						String team = event.getLine(3);
						event.setLine(2, ChatColor.DARK_PURPLE + team + " team");
						event.setLine(3, " ");
					}
				} else if (event.getLine(1).equalsIgnoreCase("leave")) {
					event.setLine(1, ChatColor.DARK_AQUA + "Leave");
					if (event.getLine(3).isEmpty()) {
						event.setLine(2, " ");
						event.setLine(3, " ");
					} else {
						String team = event.getLine(3);
						event.setLine(2, ChatColor.DARK_PURPLE + team + " team");
						event.setLine(3, " ");
					}
				}
			}
		}

	}

}
