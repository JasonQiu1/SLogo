# slogo

## Team 3

## Jason Qiu (jq48), Jordan, Jeremyah, Judy

This project implements a simple Logo ide, interpreter, and visualizer.

### Timeline

* Start Date: February 16, 2024

* Finish Date: March 8, 2024

* Hours Spent: 39 (Jason Qiu) +

### Attributions

* Resources used for learning (including AI assistance)
    * Jason Qiu
        * Interpreter : https://craftinginterpreters.com/contents.html

* Resources used directly (including AI assistance)

### Running the Program

* Main class: Main.java

* Data files needed:

* Interesting data files:

* Key/Mouse inputs:

### Notes/Assumptions

* Assumptions or Simplifications:
    * Running commands:
        * All variables are written with a preceding colon, including :repcount and iterator
          variables in for commands

* Known Bugs:

* Features implemented:
    * Commands:
        * Tokenizer handles SLOGO-01 to SLOGO-05 perfectly
        * Implemented all commands in the specification (SLOGO-06 to SLOGO-55) except those that
          interface with the view:
            * setpencolor, setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen

* Features unimplemented:
    * Commands:
        * setpencolor, setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen

* Noteworthy Features:
    * Commands:
        * Closure when defining user commands and variables
        * Does not require whitespace around list brackets
        * Lists ([expression]) can be used as groupings ((expression)) to correct precedence
        * Precedence and associativity: e.g. ~product 5 - 9 5 = 20
        * Very extendable library environments (load different library commands)
        * Very extendable commands (very easy to implement new commands)
        * Very extendable context-free grammar with auto-generator for the grammar

### Assignment Impressions

Jason Qiu: For me, I mainly tackled the challenge of the command parsing subsystem. I previously
studied a bit of formal programming languages previously, so this was a really good exercise to put
that knowledge to the test. Encountering and solving challenges such as letting the frontend know
somehow how many steps were taken with each command ran were interesting and not what I anticipated
in the beginning.