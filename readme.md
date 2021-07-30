
# Lucky Staff Mode

### Info
* Required minecraft server version: 1.8.9
* Dependencies: SuperVanish or PremiumVanish

### Compiling
To compile, you will need to add your StrikePractice plugin file to \libraries and rename it to StrikePractice.jar.

Strike Practice is a premium plugin, so I cannot put the file in this repo.

### Commands and Features
```
Commands:
/staffmode - Toggles staffmode for yourself
/staffmode <player> - Toggles staffmode for another player
/staffmode list - Lists all players in staff mode
/freeze <player> - Freezes another player
/freeze list - Lists all frozen players

Features:
- Automatically notifies staff if a frozen player disconnects and automatically re-freezes them if they re-joined in under 60 seconds.
- Automatically toggles staffmode when vanishing.
- Hooks with StrikePractice so the fight/battle will automatically end if one of the players got frozen
- Saves the player's inventory before giving them the staffmode items and restores them when the player leaves staff mode.
```

### QnA
* Custom item config where? no lol
