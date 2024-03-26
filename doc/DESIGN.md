# DESIGN Document for Slogo

### Team 3

### Jason Qiu, ...

## Team Roles and Responsibilities

* Jason Qiu: Responsible for command parsing and main external API for the model

* Team Member #2

* Team Member #3

* Team Member #4

## Design Goals

* Commands open to creating new commands and aliases with the execution of the commands being closed
  to modification.

* Goal #2

* Goal #3

#### How were Specific Features Made Easy to Add

* New commands are very easy to add by simply subclassing Command, JavaCommand, or
  InterpreterCommand
  based on your command's requirements and then registering them in the LibraryEnvironment class.

* New programming languages are also incredibly easy to integrate simply by implementing the
  CodeRunner interface.

* Feature #3

## High-level Design

#### Core Classes and Abstractions, their Responsibilities and Collaborators

* Session is the main gateway interface to the model's external API. It is responsible for modifying
  and
  reporting the state of the model based on command input. It also throws errors doped with
  debugging information if an exception occurs while running commands. It collaborates with
  CodeRunner, which manages the Slogo code environment and runs code, and Turtle, which provides a
  simple interface for sending messages to turtles in the model.

* CodeRunner is the main interface to the backend code environment. It has a single public method to
  run arbitrary strings and may throw errors that occur in different parts of the code running
  pipeline, which include tokenizing in Lexer, parsing in Parser, interpreting in Interpreter, and
  runtime errors in concrete subclasses of Command.

* Class #3

* Class #4

## Assumptions or Simplifications

* CodeRunner assumes nothing about the string being passed in and attempts to parse any string,
  including multi-line ones.

* Session assumes that the caller of its functions could possibly modify the return values, so it
  returns copies of its internal instance variables for `getCommandHistory` to prevent it from being
  modified by the caller.

* Decision #3

* Decision #4

## Changes from the Original Plan

* The addition of a way for the model to communicate with the view was required after the change
  requirement, but it was not finished.

* Change #2

* Change #3

* Change #4

## How to Add New Features

#### Features Designed to be Easy to Add

* New commands are designed to be extremely easy to add: To add a new language command, in the
  command package, simply extend JavaCommand or InterpreterCommand depending on whether or not you
  need to run native slogo code within your command or not. Then you must register the command in
  the LibraryEnvironment's defineEnvironment function by adding a new line calling defineCommand and
  the name of the command as well as an instance of its class. If you want to define aliases, you
  can do so after the command has been defined with its canonical name by using defineAlias.

* Feature #2

* Feature #3

* Feature #4

#### Features Not Yet Done

* To implement the unimplemented commands, which all interacted with the view (e.g. setpencolor,
  setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen), the view has to
  provide a listener class to be registered when each Session instance is constructed. When the
  Session is instantiated, it will pass those listeners to a decorator class that wraps
  LibraryEnvironment, which will instantiate ViewCommand classes using those listeners, which will
  be able to send messages to the view without being dependent on any view components.

* Feature #2

* Feature #3

* Feature #4
 