package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;
import com.olyno.gami.models.Team;

public class CmdTeamCreate extends GameCommand {

    public CmdTeamCreate() {
        super();
        this.setDescription("Create a new team in a game.");
        this.setPattern("create (\\w+) (in|of|from) (\\w+)");
        this.setUsage("create <team name> in <game name>");
        this.addPermission("team.create");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(2).toLowerCase();
        String teamName = args.get(0).toLowerCase();
        Gami.getGameByName(gameName).ifPresentOrElse(gameFound -> {
            Team team = new Team(teamName);
            gameFound.addTeam(team);
        }, () -> {
            this.fail("The game '" + gameName + "' has not been found.", sender);
        });
    }
    
}