package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTeamJoin extends GameCommand {

    public CmdTeamJoin() {
        super();
        this.setDescription("Join an existing team in a game.");
        this.setPattern("(\\w+) (in|of|from) (\\w+) join");
        this.setUsage("<team name> in <game name> join");
        this.addPermission("team.join");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(2).toLowerCase();
        String teamName = args.get(0).toLowerCase();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Gami.getGames().containsKey(gameName)) {
                if (Gami.getGames().get(gameName).getTeams().containsKey(teamName)) {
                    Gami.getGames().get(gameName).getTeams().get(teamName).addPlayer(player);
                } else {
                    this.fail("The team '" + teamName + "' has not been found.", sender);
                }
            } else {
                this.fail("The game '" + gameName + "' has not been found.", sender);
            }
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}