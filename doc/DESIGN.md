# DESIGN Document for Slogo

### Team 3

### Jason Qiu, Jordan Haytaian, Jeremyah Flowers, Judy He

## Team Roles and Responsibilities

* Jason Qiu: Responsible for command parsing and main external API for the model

* Jordan Haytaian: Responsible for load/save/xml functionality and part of front-end

* Jeremyah Flowers: Responsible for front-end

* Judy He: Responsible for front and back end of turtle animation

## Design Goals

* Commands open to creating new commands and aliases with the execution of the commands being closed
  to modification.

* Use the model/view/controller design pattern to promote modularity and separation of concerns

* Maintain a strong dependency hierarchy by ensuring that classes are tightly scoped and isolated in
  their responsibilities. This allows for a clean separation of concerns and reduces the likelihood
  of unintended coupling between components.

#### How were Specific Features Made Easy to Add

* New commands are very easy to add by simply subclassing Command, JavaCommand, or
  InterpreterCommand
  based on your command's requirements and then registering them in the LibraryEnvironment class.

* New programming languages are also incredibly easy to integrate simply by implementing the
  CodeRunner interface.

* New language translations could be easily added by creating a new resource bundle with the
  applicable translations, adding the language option to the language selection combobox, and
  creating a new flag for the language

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

* UIController is an abstract class that serves as the base for more specific UI Controllers. It is
  responsible for handling communication between model and view, as well as updating UI elements and
  themes. It collaborates with UIElements, Sessions, and XMLConfiguration.

* The Turtle class is a concrete class to represent the turtle. It's main responsibility is to
  manage the state of the turtle, handle movements, rotations, and updates, as well as maintain a
  history of turtle steps. It collaborates with TurtleState, ModeStrategy, TurtleStep, Point, and
  Vector.

## Assumptions or Simplifications

* CodeRunner assumes nothing about the string being passed in and attempts to parse any string,
  including multi-line ones.

* Session assumes that the caller of its functions could possibly modify the return values, so it
  returns copies of its internal instance variables for `getCommandHistory` to prevent it from being
  modified by the caller.

* We assumed that the help overview and in depth information was only intended for library commands.
  This assumption meant that library commands and user-defined commands were both stored and handled
  completely separately. Accessing library commands utilized the XMLConfigurations class while
  accessing user defined commands utilized the Session class. Had we assumed that help information
  included the user defined commands, the XMLConfiguration class would most likely have only been
  used
  by the backend. All command inquiries from the view would have gone through the Session.

* We assumed that commands stored and displayed for command history would be contained in the format
  that the user entered. For example, if the user entered 'fd 10 rt 45' to the command line, the
  history would contain the two commands as one entry instead of two separate entries.

## Changes from the Original Plan

* The addition of a way for the model to communicate with the view was required after the change
  requirement, but it was not finished.

* We originally planned to store the saved files in an XML format, but decided to save them directly
  as an SLOGO file.

* Upon realizing that the view needed to know how many turtle steps were taken after running a
  string of commands to accurately animate the turtle, the session.run() method was modified to
  return the number of turtle steps.

* Initially, we only planned to have button and commandline elements in the front end. However, in
  practice, we realized that we also needed elements to represent drop down menus, scrollable lists,
  check boxes, etc. We ended up creating a UIElement parent class with subclasses to represent these
  different elements.

## How to Add New Features

#### Features Designed to be Easy to Add

* New commands are designed to be extremely easy to add: To add a new language command, in the
  command package, simply extend JavaCommand or InterpreterCommand depending on whether or not you
  need to run native slogo code within your command or not. Then you must register the command in
  the LibraryEnvironment's defineEnvironment function by adding a new line calling defineCommand and
  the name of the command as well as an instance of its class. If you want to define aliases, you
  can do so after the command has been defined with its canonical name by using defineAlias.

* New language translations can be easily added by creating a new resource bundle with the
  applicable translations, adding the language option to the language selection combobox, and
  creating a new flag for the language. Error messages can be displayed in the selected language by
  following this process, as well.

* New menus, help screens, graphics boxes, etc. are designed to be easy to add. If a new background
  is required, simply subclass the GeneralPage class and add an option representing the type of page
  to either the SLOGO or help window classes. A new window is then created by creating a new SLOGO
  or help window.

* Our team anticipated the change requirement of having multiple turtles and accommodated for this
  feature in our initial planning. Manipulating collections of turtles instead of single turtles
  objects allows for the creation of arbitrarily many turtles.

#### Features Not Yet Done

* To implement the unimplemented commands, which all interacted with the view (e.g. setpencolor,
  setpensize, pencolor, penup, pendown, showturtle, hideturtle, clearscreen), the view has to
  provide a listener class to be registered when each Session instance is constructed. When the
  Session is instantiated, it will pass those listeners to a decorator class that wraps
  LibraryEnvironment, which will instantiate ViewCommand classes using those listeners, which will
  be able to send messages to the view without being dependent on any view components.

* Indexed Values View: This would be done similar to the implementation for the library command help
  pop up window. The first step would be adding a button to the graphics page for indexed values. In
  pageBuilder, it would be specified that when this button was pressed, a new help window should be
  opened. A new subclass of GeneralPage would be created to represent the indexed values view page.
  This page would utilize the MLConfiguration class to access the existing indexed values.

* Preferences: The preferences related to the turtle animation and graphics page are unimplemented.
  Finishing this feature would be done similarly to how the preferences related to the splash screen
  were implemented. The click of the load preferences button would notify the applicable controllers
  to establish the preferences.

* Turtle View: This would be done by creating an event triggered by clicking the turtle. This event
  would create a new help window that would display the relevant info.
 