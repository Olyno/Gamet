# GameAPI ![](https://img.shields.io/travis-ci/AlexLew95/GameAPI.svg?style=flat-square)

[![](https://img.shields.io/github/issues/AlexLew95/GameAPI.svg?style=for-the-badge)](https://github.com/AlexLew95/GameAPI/issues) ![](https://img.shields.io/github/forks/AlexLew95/GameAPI.svg?style=for-the-badge) ![](https://img.shields.io/github/stars/AlexLew95/GameAPI.svg?style=for-the-badge)

![](https://i.imgur.com/Lo4xPqv.png)

## Overview:

GameAPI is a quick and easy way to create games without the hassle. It is compatible for both java and skript developers but also for those who do not know how to code in these two languages.

## Documentations:

**__Java:__**

 - [Javadoc](https://alexlew95.github.io/GameAPI/javadoc/)

**__Skript:__**

 - [SkriptHub](http://skripthub.net/docs/?addon=GameAPI)
 - [Skunity](https://docs.skunity.com/syntax/search/addon:GameAPI)
 
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

 ## Issues, suggestions:
 
 To be organized, it will be necessary to differentiate the types of issues you have please.
 
 **__Skript:__** [here](https://github.com/AlexLew95/GameAPI/issues/new?milestone=Skript+part)
 
**__Java:__** [here](https://github.com/AlexLew95/GameAPI/issues/new?milestone=Java+part)
