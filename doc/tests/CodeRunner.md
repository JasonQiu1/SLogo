```Java
public interface CodeRunner {
  void run(String commands);
}
```

Empty Command
---
GIVEN one turtle at (0,0) heading 0deg

WHEN run("")

THEN one turtle at (0,0) heading 0deg

Position Commands
---
GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHERE _command_ in {"fd 50", "bk -50", "setxy 50 0"}

THEN one turtle at (0,50) heading 0deg

Command Aliases
---
GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHERE _command_ in {"forward 50", "back -50", "goto 50 0"}

THEN one turtle at (0,50) heading 0deg

Case Insensitive Library Commands
---
GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHERE _command_ in {"fOrwArd 50", "bACk -50", "GOto 50 0"}

THEN one turtle at (0,50) heading 0deg


Case Insensitive User Defined Commands
---
GIVEN one turtle at (0,0) heading 0deg

AND defined command with name "myfd" with arg "x" and body "fd x bk -x fd x"

WHEN run("MYFd 50")

THEN one turtle at (0,50) heading 0deg

Heading Commands
---
GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHERE _command_ in {"rt 90", "lt -90", "lt 270", "seth 90", "towards 1 0"}

THEN one turtle at (0,0) heading 90deg

Multiple Simple Commands
---

GIVEN one turtle at (0,0) heading 0deg

WHEN run(_command_)

WHERE _command_ in {"fd 100 bk 50 rt 90", "lt 270 fd 500 bk 450 fd -50 lt 90 fd 50 rt 90"}

THEN one turtle at (0,50) heading 90deg
