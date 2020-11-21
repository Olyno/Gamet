package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;
import com.olyno.gami.models.Team;

import org.bukkit.command.CommandSender;

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
        if (Gami.getGames().containsKey(gameName)) {
            Team team = new Team(teamName);
            Gami.getGames().get(gameName).getTeams().put(team.getName(), team);
        } else {
            this.fail("The game '" + gameName + "' already exists.", sender);
        }
    }
    
}