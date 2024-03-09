# slogo

## Team 3

## Jason Qiu (jq48), Jordan (jeh120), Jeremyah, Judy He (yh381)

This project implements a simple Logo ide, interpreter, and visualizer.

### Timeline

* Start Date: February 16, 2024

* Finish Date: March 8, 2024

* Hours Spent: 44 (Jason Qiu) + 40 (Judy He) + 40 (Jordan Haytaian)

### Attributions

* Resources used for learning (including AI assistance)
    * Jason Qiu
        * Interpreter : https://craftinginterpreters.com/contents.html
    * Jordan Haytaian
        * Chatgpt
    * Judy He
        * Design patterns: https://www.oodesign.com/

* Resources used directly (including AI assistance)

### Running the Program

* Main class: Main.java

* Data files needed: Translations/english.properties, Translations/french.properties,
  Translations/spanish.properties

* Interesting data files: data/preferences/preference_1.xml

* Key/Mouse inputs: Ctrl+R to submit typed text

### Notes/Assumptions

* Assumptions or Simplifications:
    * Running commands:
        * All variables are written with a preceding colon, including :repcount and iterator
          variables in for commands

* Known Bugs:
    * Each time replay is clicked, less line is drawn. The line is not drawing properly for repeated replays.
    * Animation starts lagging if changing from high speed to lower speed.
    * Replay does not function properly after changing speed
    * Load throws error from splash screen, works properly in IDE
    * Running a command from a help menu that does not take parameters will prompt for parameters
    * Some language translations and help documentation make buttons overlap or text flow off-screen

* Features implemented:
    * Commands:
        * Tokenizer handles SLOGO-01 to SLOGO-05 perfectly
        * Implemented all commands in the specification (SLOGO-06 to SLOGO-55) except those that
          interface with the view:
            * setpencolor, setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen
        * Runs all commands until encountering an error
        * Throws an error which should be caught by the view and displayed to the user when
          encountering an error with the given code
    * Help Pages:
        * Commands, Variables, and History can all be displayed in a pop-up window
        * Variables can be set by clicking on them in the pop-up
        * Commands can be run by clicking on them in the pop-up
    * Preferences:
        * User can load preferences for SplashScreen
    * Load/Save
        * User can load and save programs to/from .slogo files
    * Language Translation
        * User can translate UI to English, Spanish, or French

* Features unimplemented:
    * Commands:
        * setpencolor, setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen
    * Preferences:
        * XML Graphics preferences (loading file, background color, pen color, index values, num
          turtles)

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
    * Help Pages:
        * Commands, Variables, and History can all be displayed in a pop-up window
        * Variables can be set by clicking on them in the pop-up
        * Commands can be run by clicking on them in the pop-up

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

Jordan Haytaian: I learned a lot about design patterns through working on this project. For example,
it was my first time working with controllers/listeners. This experience was also my first time
working extensively in front-end development. I really enjoyed working with graphics and making
design considerations about the user's visual experience. I found this project to be very
collaborative in that I was often working in classes that other group members had created. This was
a great exercise in interpreting and writing compatible code.
