package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdTeamMinimumPlayer extends GameCommand {

    public CmdTeamMinimumPlayer() {
        super();
        this.setDescription("Define the minimum of player before be able to start the game.");
        this.setPattern("(\\w+) (in|of|from) (\\w+) set minimum player ([0-9]+)");
        this.setUsage("<team name> in <game name> set minimum player <amount>");
        this.addPermission("team.set.players.minimum");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(2).toLowerCase();
        String teamName = args.get(0).toLowerCase();
        Integer minPlayer = Integer.valueOf(args.get(3));
        Gami.getGameByName(gameName)
            .stream()
            .flatMap(gameFound -> gameFound.getTeamByName(teamName).stream())
            .findFirst()
            .ifPresentOrElse(
                teamFound -> teamFound.setMaxPlayer(minPlayer),
                () -> this.fail("The team '" + teamName + "' has not been found.", sender)
            );
    }
    
}