# slogo

## Team 3

## Jason Qiu (jq48), Jordan, Jeremyah, Judy He (yh381)

This project implements a simple Logo ide, interpreter, and visualizer.

### Timeline

* Start Date: February 16, 2024

* Finish Date: March 8, 2024

* Hours Spent: 39 (Jason Qiu) + 40 (Judy He) +

### Attributions

* Resources used for learning (including AI assistance)
    * Jason Qiu
        * Interpreter : https://craftinginterpreters.com/contents.html
    * Judy He
        * Design patterns: https://www.oodesign.com/

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
    * Replay does not function properly after changing speed

* Features implemented:
    * Commands:
        * Tokenizer handles SLOGO-01 to SLOGO-05 perfectly
        * Implemented all commands in the specification (SLOGO-06 to SLOGO-55) except those that
          interface with the view:
            * setpencolor, setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen
        * Runs all commands until encountering an error
        * Throws an error which should be caught by the view and displayed to the user when
          encountering an error with the given code

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
        * Throws custom exceptions with translatable debugging info for where the error occurred
          when running code, which includes line number, the raw line of code itself, and where in
          the running process the error occurred (tokenizing, parsing, interpreting, runtime, etc.)
    * Animation:
        * Play, pause, step through, replay animation
        * Animation supports 3 modes (wrap, window, fence) and can be easily extended to support new
          modes that controls the turtle's behavior when it reaches the edge.
        * Draw line, erase line
        * User may speed up/slow down both ongoing and future animations

### Assignment Impressions

Jason Qiu: For me, I mainly tackled the challenge of the command parsing subsystem. I previously
studied a bit of formal programming languages previously, so this was a really good exercise to put
that knowledge to the test. Encountering and solving challenges such as letting the frontend know
somehow how many steps were taken with each command ran were interesting and not what I anticipated
in the beginning.

Judy He: My primary responsibility for this project was implementing both the model and the view
part of the turtle animation. It was challenging at times to compute the math behind the turtle
motion and designing the API in such a way that it supports speed adjustments, the wrap mode and
tracks the step history of the turtles. As I helped integrated the animation part of the model with
view, I encountered further challenges in displaying the intermediate states of the turtle computed
by the model as well as generating the line animation. Another challenge was implementing the View
to support change in animation speed.    