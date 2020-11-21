package com.olyno.gamet.events.bukkit;

import java.util.HashMap;
import java.util.regex.Pattern;

import com.olyno.gamet.Gamet;
import com.olyno.gami.Gami;
import com.olyno.gami.models.Game;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * To join a team:
 * 
 * [UHC] 
 * Join
 * Team
 * Red
 */

/**
 * To join a game:
 * 
 * [UHC]
 * Join
 */

public class Signs implements Listener {

	private HashMap<Location, Game> signLocations = new HashMap<Location, Game>();

	public Signs( ) { }

	@EventHandler
	public void onGameSignClicked(PlayerInteractEvent event) {
		if (Gamet.manage_automatically) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock();
				if (signLocations.containsKey(block.getLocation())) {
					if (block.getType().toString().contains("SIGN")) {
						Sign sign = (Sign) block.getState();
						String gameName = ChatColor.stripColor(sign.getLine(0)).replaceAll("\\[|\\]", "");
						if (ChatColor.stripColor(sign.getLine(1)).equalsIgnoreCase("join")) {
							if (sign.getLine(2).replaceAll(" ", "").isEmpty()) {
								if (event.getPlayer().hasPermission("game.join")) {
									Gami.getGames().get(gameName).addPlayer(event.getPlayer());
								} else {
									event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to join this game.");
								}
							} else {
								String teamName = ChatColor.stripColor(sign.getLine(2)).replaceAll("\\s+\\S+$", "");
								Gami.getGames().get(gameName).getTeam(teamName).addPlayer(event.getPlayer());
							}
						} else if (ChatColor.stripColor(sign.getLine(1)).equalsIgnoreCase("leave")) {
							if (sign.getLine(2).replaceAll(" ", "").isEmpty()) {
								if (event.getPlayer().hasPermission("game.leave")) {
									Gami.getGames().get(gameName).removePlayer(event.getPlayer());
								} else {
									event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to leave this game.");
								}
							} else {
								String teamName = ChatColor.stripColor(sign.getLine(2)).replaceAll("\\s+\\S+$", "");
								Gami.getGames().get(gameName).getTeam(teamName).removePlayer(event.getPlayer());
							}
						}
					}
				}
			}
		}

	}

	@EventHandler
	public void onGameSignPlaced(SignChangeEvent event) {
		if (Gamet.manage_automatically) {
			if (Pattern.compile("\\[\\w+\\]").matcher(event.getLine(0)).find()) {
				String gameName = event.getLine(2).replaceAll("\\[|\\]", "");
				if (Gami.getGames().containsKey(gameName)) {
					event.setLine(0, ChatColor.GOLD + "[" + ChatColor.YELLOW + gameName + ChatColor.GOLD + "]");
					if (event.getLine(1).equalsIgnoreCase("join")
						|| event.getLine(1).equalsIgnoreCase("leave")
					) {
						event.setLine(1, ChatColor.DARK_AQUA + capitalize( event.getLine(1).toLowerCase() ));
						if (event.getLine(3).isEmpty()) {
							event.setLine(2, " ");
							event.setLine(3, " ");
						} else {
							String teamName = event.getLine(3);
							event.setLine(2, ChatColor.DARK_PURPLE + teamName + " team");
							event.setLine(3, " ");
						}
						signLocations.put(event.getBlock().getLocation(), Gami.getGames().get(gameName));
					}
				}
			}
		}

	}

	public String capitalize(String str) {
		if (str == null || str.isEmpty()) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
