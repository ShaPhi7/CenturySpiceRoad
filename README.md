# CenturySpiceRoad
A simple Java implementation of the board game Century: Spice Road. Rules to the game can be found at https://boardgamegeek.com/boardgame/209685/century-spice-road

This project contains the backend implementation required to run a game of Century Spice Road from start to finish.
There are 4 actions that a player can do (play, acquire, rest and claim). These actions currently live in the game components classes to which they are associated.

## The To-do List

Next steps intention:
- Present an API that anyone is able to hook into
- Hook into this API with a Javascript UI
- Package into a container to provide as a service
- Add CPU players
- Train an AI to play the game effectively as a CPU

A note on the CLI included in this project:
Unfortunately, I had the brilliant idea of implementing a quick CLI frontend, as I told myself this would be quicker than a full UI. This is unfortunate because I forgot that beyond the most basic of use cases, CLIs are never really quick to implement, and are always as much of a pain to work with as just creating a proper UI. So although the backend code should be ok and reasonably tested, the CLI portions (which are clearly marked in CLI classes - you can't miss them) are not, and I have no intention of going back to improve these. We'll just move them out the way in favour of nicer front ends in a future update. **Developers don't do stuff because it's easy, they do it because they THINK it's going to be easy!**
