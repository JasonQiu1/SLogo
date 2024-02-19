# SLogo Design Plan

### Judy He, Jason Qiu, Jordan Haytaian, Jeremyah Flowers

### TEAM #3

#### Examples

Here is a graphical look at my design:

![This is cool, too bad you can't see it](online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).

Here is our amazing UI:

![This is cool, too bad you can't see it](29-sketched-ui-wireframe.jpg "An alternate design")

taken
from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).

## Introduction

## Configuration File Format

## Design Overview

The UI design will be handled by the Frontend internal and external API. The user will be able
to interact with the UI mainly through mouse events and the GUI's built-in command line. The 
user will be able to input a command (i.e, `fd`, `bk`, etc.), and see the turtle being animated 
in real time. These input commands will be parsed by the model and passed via a method such as
`passCommandToBeParsed`. The user will also be able to click different buttons such as pause/play,
2x speed, 5x speed, and pen colors. These will all be filtered using classes called `Input` 
and `Animation`. Finally, the user can change the turtle or load a saved configuration via a 
file selector. These interactions will be handled through a `XML` configuration class.
Updating and processing the turtle animation will be handled by the external and internal Model API.
To keep track of the turtle's position and heading, we will implement a `Point` class and a `Vector`
class as our bottom level of abstraction. The Point class will be used to track position while the
Vector class will be used to track angle and magnitude, the turtle's displacement between steps. To
calculate the turtle's next position or new heading, we will have a static protected
class `TurtleGeometry`, handling all `Vector` and `Point` related operations.
Moving up the levels of abstraction, to keep track of a turtle's sequence of steps, we will
implement a `Step` class that contains attributes like initial and final position (`Point` objects)
and initial and final vector (`Vector` objects) as well as whether the pen is down for that step.
This will allow us to efficiently step forward or back in the animation, replay the animation, and
change in the animation speed in the View. Moving further up the abstraction layers, we will
implement a `Turtle` class, which will contain attributes storing the turtle's unique id (this is to
allow to a possible future extension of having multiple turtles), initial position at the start of
the animation (`Point`), current position (`Point`), heading and the sequence of `Step` it has
taken.
Lastly, at the very top level of abstraction, we will have the static `TurtleAnimator` class which
defines the scrunch, the current graphics scaling factor, and the bounds of the animation window and
checks whether a `Turtle` is in bound after a `Step`. Overall, by encapsulating all the logic behind
the turtle's animation in the backend external and internal APIs, the View will only need to call
specific methods like `doStep()` or `getHeading()` and will receive the final position or facing
position of the turtle.

## Design Details
`Page`
This abstract class creates a new page. This can be passed several different implementations of a 
page including the GUI for turtle animation and the splash screen.

`SlogoWindow`
This class is the main area the user interacts with the animation, it can used to create any 
number of pages. Any user interactions with the window will automatically go the page and vice 
versa.

`Button`
This abstract class will have different implementations of the buttons needed to process user
queries. For example, there will be load/save, pause/play, speed buttons.

`CommandLine`
This class displays and takes in information from the built-in command line. It should be called on
by the parser.

`Observer/Controller`
This class will wait for user queries passed via the buttons and pass the information it receives.

`Point`

This class is part of the Model internal API and will contain 2 attributes of type double: x, y.
This is one of the classes at the bottom level of abstraction for representing a position in the
animation. Classes related to turtle animation in the backend internal and external APIs as well as
the Controller in the frontend will depend on this class to update the turtle's position and the
drawing.

`Vector`

This class is part of the Model internal API and will contain attributes of type double: dx, dy,
angle, magnitude. This is one of the classes at the bottom level of abstraction for calculating
distance and angle between `Point` objects. Classes related to turtle animation in the backend
internal API will depend on this class.

`Step`

This class is part of the Model internal and external APIs and is at the next higher level of
abstraction. It will contain attributes: `Point` startPostion, `Point` endPosition, `Vector`
initialVector, `Vector` finalVector, double changeInMagnitude, double changeInAngle. A `Step` object
represents a step, a change in distance or heading, the turtle has taken. This satisfies SLOGO 75
and 76. To replay an animation, the View will only need to retrieve the list of Step objects for the
turtle. To pause, play step forward, or step back in the animation, one will only need to trace the
list of Step object.

`TurtleGeometry`

This class will be a protected static class part of the Model internal API, another layer of
abstraction, that handles all operations related to calculating values such as the distance
travelled, the next position, and the heading of the turtle. Methods will
include: `calculateAngle(Vector v1, Vector v2), calculateMagnitude(Vector v),
calculateXComponent(Vector v, double angle), calculateYComponent(Vector v, double angle),
calculateFinalPosition(Point initialPosition, double distance), 
double dotProduct(Vector v1, Vector v2), 
double crossProduct(Vector v1, Vector v2)`.
The method signatures do not reveal the implementation details of the turtle animation used given
that the TurtleGeometry only performs operations on the more abstract classes `Point` and `Vector`.

`Turtle`

This class will represent a turtle in the animation and is towards the top level of abstraction. It
will depend on the more abstract classes `Point`, `Vector`, `Step`, `TurtleGeometry`. Attributes
include the turtle's: int id, Point currentPosition, Point initialPosition, double heading,
List<step> stepHistory. In the Model internal API, the methods `move(double distance)`
and `rotate(double angle)` allow the turtle to update its position or heading. In the Model external
API, user will be able to the get all the turtle's attributes as well as call public methods
including: `getHeadingTowards(Point point), doStep(double length, double angle), stepForward(), 
stepBack(), reset()`.
These method signatures keep all implementation details encapsulated given that one is not able to
read any data structures or classes involved at the bottom level of abstraction.

`TurtleAnimator`

This class will be public static and is at the top level of abstraction. It is for storing all
parameters related to the animation environment. Attributes include the bounds of the animation
window to be generated by the View, the graphic scaling factor, and the turtle(s) in the animation.
As part of the Model internal API, it will contain a method `checkBounds()` to validate whether the
turtle is within bound of the animation window after performing a `Step`. As part of the Model
external API, this class will only return all its attributes, thereby encapsulating all the
implementation details of the turtle animation from the user.

## Design Considerations

Session?

## Test Plan

## Team Responsibilities

* Team Member #1: Judy He
    * Primary: Implement the part of the Model internal and external APIs for animating the turtle.
      Debug all issues related to turtle animation.
    * Secondary: Create test cases, help teammates with debugging and planning.

* Team Member #2: Jeremyah Flowers
    * Primary: Implement the external portion of the Frontend/View API and the UI display
    * Secondary: Help with internal framework for button/error handling

* Team Member #3

* Team Member #4

### Timeline
