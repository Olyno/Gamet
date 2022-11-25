package com.olyno.gamet.commands.team;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.olyno.gamet.util.commands.GameCommand;
import com.olyno.gami.Gami;

public class CmdTeamLeave extends GameCommand {

    public CmdTeamLeave() {
        super();
        this.setDescription("Leave an existing team in a game.");
        this.setPattern("(\\w+) (in|of|from) (\\w+) leave");
        this.setUsage("<team name> in <game name> leave");
        this.addPermission("team.leave");
    }

    @Override
    public void execute(CommandSender sender, ArrayList<String> args) {
        String gameName = args.get(2).toLowerCase();
        String teamName = args.get(0).toLowerCase();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Gami.getGameByName(gameName)
                .stream()
                .flatMap(gameFound -> gameFound.getTeamByName(teamName).stream())
                .findFirst()
                .ifPresentOrElse(
                    teamFound -> teamFound.removePlayer(player),
                    () -> this.fail("The team '" + teamName + "' has not been found.", sender)
                );
        } else {
            this.fail("Can't execute this command in the console.", sender);
        }
    }
    
}