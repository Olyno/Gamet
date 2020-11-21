package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;

public class CmdTeamMaximumPlayer extends GameCommand {

    public CmdTeamMaximumPlayer() {
        super();
        this.setDescription("Define the maximum of player before start the game.");
        this.setPattern("(\\w+) (in|of|from) (\\w+) set maximum player ([0-9]+)");
        this.setUsage("<team name> in <game name> set maximum player <amount>");
        this.addPermission("team.set.players.maximum");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(2).toLowerCase();
        String teamName = args.get(0).toLowerCase();
        if (Gami.getGames().containsKey(gameName)) {
            if (Gami.getGames().get(gameName).getTeams().containsKey(teamName)) {
                Gami.getGames().get(gameName).getTeams().get(teamName).setMaxPlayer(Integer.valueOf(args.get(3)));
            } else {
                this.fail("The team '" + teamName + "' has not been found.", sender);
            }
        } else {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        }
    }
    
}