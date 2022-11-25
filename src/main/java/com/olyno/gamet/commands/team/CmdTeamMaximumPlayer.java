package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

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
        Integer maxPlayer = Integer.valueOf(args.get(3));
        Gami.getGameByName(gameName)
            .stream()
            .flatMap(gameFound -> gameFound.getTeamByName(teamName).stream())
            .findFirst()
            .ifPresentOrElse(
                teamFound -> teamFound.setMaxPlayer(maxPlayer),
                () -> this.fail("The team '" + teamName + "' has not been found.", sender)
            );
    }
    
}