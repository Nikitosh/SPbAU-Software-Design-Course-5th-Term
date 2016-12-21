# Roguelike

### Overview

Roguelike game prototype.

### Features

* User's hero (```@```).
* Two types of mobs available: orcs (```O```) and trolls (```T```).
* Two types of items available: kit (```+```) and shield (```*```).
* Mobs use random walking strategy and beat user's hero if they can.
* All game objects have attributes: health, attack and defense.
* Game uses map, which should be in ```src/main/resources/map.txt``` file.

### Game controls

* ```w``` -- move up.
* ```a``` -- move left.
* ```s``` -- move down.
* ```d``` -- move right.
* ```1-9``` -- use inventory item.


### Project structure

* All game objects have base GameObject class, which they should extend. Every game object has it's own position and attributes.
* There are base classes for mobs and items too. This provides easy adding of new mobs and items.
* Mobs moving strategies and controls for player are implemented with ```Startegy``` interface which has only one method ```getMove()```.
* Position is class for showing where the game object is. It just contains x- and y- coordinates.
* WorldMap creates new map from description in text file.
* World generates all game stuff and performs interaction between all game objects.

* DrawVisitor is used for drawing world's map separately from logic.
* UI is implemented with ```AsciiPanel``` library (you can read about it and install it [here]).

### Used patterns

* ```Factory``` pattern for creating mobs and items.
* ```Builder``` pattern for creating Attributes. It allows to add some new attributes without changing previous code.
* ```Strategy``` pattern for creating mobs moving strategy. It allows to add new controls for player and new strategies for mobs and combine them.
* ```Visitor``` pattern for drawing world's map separately from logic. 

### Screenshot

![Screenshot] (https://github.com/Nikitosh/SPbAU-Software-Design-Course-5th-Term/blob/11-roguelike/screenshot/screenshot.jpg)

[here]: <https://github.com/trystan/AsciiPanel>
