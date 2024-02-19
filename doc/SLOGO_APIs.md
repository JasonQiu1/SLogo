# SLogo API Design Lab Discussion
### Judy He, Jason Qiu, Jordan Haytaian, Jeremyah Flowers
### TEAM #3


## Planning Questions

 * What behaviors (methods) should the Turtle have and what service should it provide?

 * When does parsing need to take place and what does it need to start properly?

 * What is the result of parsing (not the details of the algorithm) and who receives it?

 * When are errors detected and how are they reported?

 * What do different commands need to know, when do they know it, and how do they get it?

 * What behaviors does the result of a command need to have to be used by the View? 

 * How is the View updated after a command has completed execution?

 * What value would Controller(s) have in mediating between the Model and View?
 


## APIs
 
### Model/Backend External API

 * Goals
   * To allow the View to animate the turtle by inputting a distance or angle and receiving the final position or facing position without needing to understand any implementation details.
 
 * Contract 
   * All classes related to turtle animation will assume that the command is parsed and the distance or angle is passed in if turtle moves forward or turns.
   * All classes related to turtle animation will collaborate to update the turtle's position or heading. 

 * Services
   * Return the animation window's bounds, graphics scaling factor, turtle object
   * Return the turtle's current position, initial postion, heading, step history
   * Return the turtle's heading towards a given position
   * Perform a step on turtle, step forward and backward in a turtle's step history, reset the turtle's position adn heading 


### View/Frontend External API

 * Goals
   * To handle UI interactions and queries such as loads, saves, animation speed, pen colors
   * To display internal date when requested, Splash Screen, GUI, etc.
   
 * Contract
   * All classes related to display will assume that the data is parsed and passed INTO the class.
   * All classes related to interactions will assume the data needs to be handled and SENT
 
 * Services
   * Methods to display/show window, page, turtle animation, buttons, etc.
   * Provides an interface the user can communication with.
   * Passes internal information to be processed/handled

### Model/Backend Internal API

 * Goals
   * To perform all math operations for updating the turtle's position and heading based on the commands received.
   * To keep track of the history of steps taken by the turtle for efficient animation replay, speeding up the animation, and stepping forward or backward in the animation history. 
 
 * Contract
   * All classes related to turtle animation will assume that the turtle's motion can be represented by interactions between position and vectors and that each turtle step represent a corresponding postion/vector operation.
   * All classes related to turtle animation will collaborate to calculate the turtle's new position or heading.

 * Services
   * move and rotate turtle
   * check if turtle in bound after a step
   * calculate angle, magnitude, x and y components of vectors
   * calculate dot product and cross product of vectors
   * calculate final postion given distance and initial position or facing postion given angle and initial position.

### View/Frontend Internal API

 * Goals
   * To handle user queries, take information, and pass that information to be displayed.
   * To handle XML configurations, errors, and pass exceptions.

 * Contract
   * All classes related to the internal API assume that the data will be processed before being 
     sent to the external API.
   * All classes related to the internal API assume that any data that is needed is to be passed 
     from the backend (or an XML file).
 
 * Services
   * Pass data to be displayed
   * Parse data
   * Pass config 
   * Handle query (or button)
   * Handle error
   * Pass exception

## Design

### Backend Design CRCs


This class's purpose or value is to represent a position in the animation environment:
```java
public class Point {
  // returns x coordinate of position
  public double getX()
  // returns y coordinate of position
  public double getY()
}
```
This class's purpose or value is to represent a vector in the animation environment:
```java
protected class Vector {
  // returns change in x position of vector
  protected double getDx()
  // returns change y postion of vector
  protected double getDy()
  // returns angle of vector
  protected double getAngle()
  // returns magnitude of vector
  protected double getMagnitude()  
    
}
```
This class's purpose or value is to represent a turtle's step in the animation environment:
```java
public class Step {
  // returns start position of a turtle step
  public Point getInitialPos()
  // returns end position of a turtle step
  public Point getFinalPos()
  // returns start vector of a turtle step
  protected Vector getInitialVector()
  // returns end vector of a turtle step
  protected Vector getFinalVector()
  // returns length of a turtle step
  public double getLength()
  // returns change in angle of a turtle step
  public double getChangeInAngle()
  // returns if the pen is down during a turtle step
  public boolean isPenDown()

}
```
This class's purpose or value is to perform the necessary math operations to calculate a turtle's next position or heading
```java
protected static class TurtleGeometry {
  // return angle between 2 vectors
  protected static double calculateAngle(Vector v1, Vector v2)
  // return magnitude of vector  
  protected static double calculateMagnitude(Vector v)
  // return x component of vector
  protected static Vector calculateXComponent(Vector v, double angle)
  // return y component of vector
  protected static Vector calculateYComponent(Vector v, double angle)
  // calculate and return final position given initial position and distance to be travelled
  protected static Point calculateFinalPosition(Point initialPosition, double distance)
  // calculate and return final facing position given initial position and angle turned
  protected static Point calculateFacingPosition(Point initialPosition, double angle)
  // calculate and return dot product of 2 vectors
  protected static double dotProduct(Vector v1, Vector v2)
  // calculate and return cross product of 2 vectors
  protected static Vector crossProduct(Vector v1, Vector v2)
    
}
```
This class's purpose or value is to represent a turtle in the animation
```java
protected class Turtle {
  // return the unique id identifying the turtle
  public int getId()
  // return turtle current position   
  public Point getCurrentPosition()
  // return turtle initial position
  public Point getStartingPosition()
  // return turtle's history of steps
  public List<Step> getStepHistory()
  // return the step turtle is currently on in its step history  
  protected int getCurrentPointInStepHistory()
   // update the step turtle is currently on in its step history  
   protected int setCurrentPointInStepHistory()
   // return turtle's heading towards given point
  public double getHeadingTowards(Point point)
  // returns turtle's heading   
  public double getHeading()
  // update turtle's position/heading and return turtle's final position/facing position (a point on the edge of screen)
  public Step doStep (length, angle)
  // go back one step in the turtle's step history and return turtle's final position/facing position (a point on the edge of screen)  
  public Step stepBack()
  // go forwards one step in the turtle's step history and return turtle's final position/facing position (a point on the edge of screen)    
  public Step stepForward()
  // reset turtle position and return its initial position  
  public Point reset()
  // move a turtle forward given distance
  protected void move(double distance)
  // turn a turtle given angle
  protected void rotate(double angle)
}
```

This class's purpose or value is to represent the animation environment
```java
protected static class TurtleAnimator {
  // return the north, east, south, west boundaries the animation window should use
  public static Map<String, Double> getBounds()
  // return the graphics scaling factor the animation window should use
  public static double getGraphicsScalingFactor()
  // return the turtle(s) in the animation environment
  public static List<Turtle> getTurtles()
  // check if turtle is within the animation window after a step
  protected static boolean checkInBound(Point position)
}
```




This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](order_crc_card.png "Order Class")

This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |

This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```


### Frontend Design CRCs


This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](order_crc_card.png "Order Class")

This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |

This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```

This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```



### Use Cases

#### Example Use Cases: [Java File](plan/usecases/ExampleUseCases.java)
 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

 * The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

 * The user changes the color of the environment's background.

#### Judy He: [Java File](plan/usecases/JudyUseCases.java)
1. SLOGO 76, 77: The user clicks on replay animation and sees the animation resets and reruns its previous series of commands.
2. SLOGO 75, 77: The user clicks on step back in the animation and sees the animation go back one command.
3. SLOGO 75, 77: The user clicks on step forward in the animation when there is no more commands in the turtle's step history.
4. SLOGO 74-77: The user clicks on clear animation and sees the turtle position resets and the drawing clears.





