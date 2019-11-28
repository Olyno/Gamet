# Gamet

[![](https://img.shields.io/github/issues/Olyno/Gamet.svg?style=for-the-badge)](https://github.com/Olyno/Gamet/issues) [![](https://img.shields.io/github/forks/Olyno/Gamet.svg?style=for-the-badge)](https://github.com/Olyno/Gamet/forks) [![](https://img.shields.io/github/stars/Olyno/Gamet.svg?style=for-the-badge)](https://github.com/Olyno/Gamet/stars) ![Bitbucket Pipelines](https://img.shields.io/bitbucket/pipelines/Olyno/Gamet.svg?label=Nightly%20build&style=for-the-badge)

![](https://i.imgur.com/Lo4xPqv.png)

## Overview:

Gamet is a quick and easy way to create games without the hassle. It is compatible for both java and skript developers but also for those who do not know how to code in these two languages.

## Documentations:

**__Java:__**

 - [Javadoc](https://olyno.github.io/Gamet/javadoc/)

**__Skript:__**

 - [SkriptHub](http://skripthub.net/docs/?addon=Gamet)
 - [Skunity](https://docs.skunity.com/syntax/search/addon:Gamet)
 
**__Other:__**

 - Nothing for the moment :(

## Commands:

```
/game create <game name>
/game delete <game name>
/game <game name> set minimum player <integer>
/game <game name> set maximum player <integer>
/game <game name> set maximum points <integer>
/game <game name> set lobby
/game <game name> spawn

/team create <team name> in <game name>
/team delete <team name> in <game name>
/team <team name> in <game name> join
/team <team name> in <game name> leave
/team <team name> in <game name> set minimum player <integer>
/team <team name> in <game name> set maximum player <integer>
/team <team name> in <game name> set spawn
/team <team name> in <game name> set lobby
```

> ``in`` in these examples is not the only word that you can use. You can replace ``in`` by ``of`` or ``from``. Chose the best syntax for you.

## Permissions:

| Games                    | Teams                    |
|--------------------------|--------------------------|
| game.create              | team.create              |
| game.delete              | team.delete              |
| game.start               | team.join                |
| game.stop                | team.leave               |
| game.join                | team.set.lobby           |
| game.leave               | team.set.spawn           |
| game.set.lobby           | team.set.players.minimum |
| game.set.spawn           | team.set.players.maximum |
| game.set.players.minimum | team.add.points          |
| game.set.players.maximum | team.remove.points       |
| game.add.players         | team.add.players         |
| game.remove.players      | team.remove.players      |

## Custom signs:

> Gamet as plugin contains the possibility to have a custom sign to join or leave a game or a team. To do that, place a sign somewhere and put that in the sign (line per line) ("/" means the first or the second, it's a choice):

```
[Gamet]
join/leave
name of your game
name of your team (optional)
```



## What Gamet manage itself:

 - [x] Multi Arenas
 - [x] Save games and possibility to share our games
 - [x] Auto sign to join or leave a game or a team
 - [ ] Save players data (like money, exp etc...) (not yet)
 - [ ] Support Bungeecord (not yet)

 ## Issues, suggestions:
 
 To be organized, it will be necessary to differentiate the types of issues you have please.
 
 **__Skript:__** [here](https://github.com/Olyno/Gamet/issues/new?milestone=Skript+part)
 
**__Java:__** [here](https://github.com/Olyno/Gamet/issues/new?milestone=Java+part)
