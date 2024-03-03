```Java
public interface Session {

  void run(String commands);

  Map<String, double> getVariables();

  Map<String, Map<String, String>> getUserDefinedCommands();

  Map<String, Map<String, String>> getLibraryCommands();

  Map<String, Map<String, String>> getCommandHistory(int length);

  Map<double, List<TurtleStep>> getTurtleStepHistory(int length);

  boolean undo(int times);

  boolean redo(int times);

  void reset();
} 
```

Multiple Simple Commands
---

GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHEN _command_ in `{"fd 100 bk 50 rt 90", "lt 270 fd 500 bk 450 fd -50 lt 90 fd 50 rt 90"}`

THEN one turtle at (0,50) heading 90deg

Variables
---
GIVEN only one variable :distance is defined with value 10

WHEN `getVariables()`

THEN `{"distance": 10}`

No Variables
---
GIVEN no variables defined

WHEN `getVariables()`

THEN `{}`

Get User Defined Commands
---
GIVEN only one user command is defined: `myfd` with two parameters `:x :y`
and body `fd :x * 2 bk :y`

WHEN `getUserDefinedCommands()`

THEN `{"myfd": {"arity": "2", "parameter1": "x", "parameter2": "y", "body": "fd :x * 2 bk :y"}}`

Get Library Commands
---
GIVEN

WHEN getLibraryCommands()

THEN map of all library commands in the following format: `{"commandName":
{"alias":"aliasName", "arity": "#", "parameter1": "par1", "parameter2": "par2"...}}`

No Commands
---
GIVEN no user commands defined

WHEN `getUserDefinedCommands()`

THEN `{}`

Empty Command History
---
GIVEN no commands have been executed

WHEN `getCommandHistory(0 OR 9)`

THEN `{}`

Command History
---
GIVEN only two commands have been executed: "fd 50" and "bke 50"

WHEN `getCommandHistory(1)`

THEN `{"bke 50": {"successful": "false"}}`

All Command History
---
GIVEN only two commands have been executed: "fd 50" and "bke 50"

WHEN `getCommandHistory(0)`

THEN `{"fd 50": {"successful": "true"}, "bke 50": {"successful": "false"}}`

Turtle Current States on Startup
---
GIVEN no commands have been executed

WHEN `getTurtlesCurrentStates()`

THEN `{0: new TurtleState(new Position(0,0), 0)}`

Turtle Current States after command
---
GIVEN "fd 50", "rt 50" has been executed

WHEN `getTurtlesCurrentStates()`

THEN `{0: new TurtleState(new Position(0,50), 50)}`


Empty Turtles Step Histories
---
GIVEN no commands have been executed

WHEN `getCommandHistory(0 OR 9)`

THEN `{}`

Turtles Step Histories
---
GIVEN only two commands have been executed: "fd 50" and "bk 50"

WHEN `getTurtlesStepHistory(1)`

THEN `{0:{new TurtleStep(new TurtleState(new Position(0, 0), 0), new Vector(0, 50), 0)}}`

All Turtles Step Histories
---
GIVEN only two commands have been executed: "fd 50" and "bk 50"

WHEN `getCommandHistory(0)`

THEN `{0:{new TurtleStep(new TurtleState(new Position(0, 0), 0), new Vector(0, 50), 0),
new TurtleStep(new TurtleState(new Position(0, 50), 0), new Vector(0, -50), 0)}}`

Empty Undo
---
GIVEN only one command "fd 50" has been executed

WHEN `undo(1)`

THEN `getCommandHistory(0) == {}` AND `getTurtlesStepHistory(0) == {}`

Nonempty Undo
---
GIVEN two commands "fd 50", "bk 50" have been executed

WHEN `undo(1)`

THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}}`
AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0, 50), 0)}}`

Undo Max
---
GIVEN only two commands have been executed: "fd 50" and "bk 50"

WHEN `undo(0)`

THEN `getCommandHistory(0) == {}` AND `getTurtlesStepHistory(0) == {}`

Undo Too Far
---
GIVEN only one command "fd 50" has been executed

WHEN `undo(3)`

THEN `getCommandHistory(0) == {}` AND `getTurtlesStepHistory(0) == {}`

Empty Redo
---
GIVEN no commands have been executed

WHEN `redo(1)`

THEN `getCommandHistory(0) == {}`
AND `getTurtlesStepHistory(0) == {}`

Redo
---
GIVEN two commands "fd 50" have been executed
AND `undo(1)` has been executed

WHEN `redo(1)`

THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}}`
AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0, 50), 0)}}`

Redo Max
---
GIVEN two commands "fd 50", "bk 50" have been executed
AND `undo(0)` has been executed

WHEN `redo(0)`

THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}, "bk 50": {"successful":"true"}`
AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0, 50), 0)},
new TurtleStep(new Position(0, 50), 0, new Vector(0, -50), 0)}}`

Redo Over
---
GIVEN two commands "fd 50", "bk 50" have been executed
AND `undo(0)` has been executed

WHEN `redo(5)`

THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}, "bk 50": {"successful":"true"}`
AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0, 50), 0)},
new TurtleStep(new Position(0, 50), 0, new Vector(0, -50), 0)}}`


Unsuccessful Commands Redo/Undo
---
GIVEN three separate commands "fd 50", "notacommand 180 fd 12", "bk 50" have been executed
AND `undo(2)` has been executed

WHEN `redo(2)`

THEN `getCommandHistory(0) == {"fd 50": {"successful":"true"}, "notacommand 180 fd 12": {"sucessful", "false"}, "bk 50": {"successful":"true"}`
AND `getTurtlesStepHistory(0) == {0:{new TurtleStep(new Position(0, 0), 0, new Vector(0, 50), 0)},
new TurtleStep(new Position(0, 50), 0, new Vector(0, -50), 0)}}`

Reset
---
GIVEN one turtle at (0,50) heading 274, with command history "goto 0 50" and "seth 274"

WHEN `reset()`

THEN one turtle at (0,0) heading 0
AND `getCommandHistory(0) == {}` 
AND `getTurtlesStepHistory(0) == {}`