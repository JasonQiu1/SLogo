# Cell Society API Lab Discussion
### Jason Qiu (jq48), Judy He (yh381), Jeremyah Flowers (jtf45), Jordan Haytaian (jeh120)
### TEAM 3

## Current Model API

### External

* Shape: RectangleShape, HexagonShape
    * getVertices()
* Cell: PercolationCell, SugarCell, SchellingCell, FireCell, LifeCell, WatorCell, FallingSandCell
    *  public void initializeNeighbors(Neighborhood hoodType, Grid grid)
    * public abstract void transition();
    * public int getCurrentState()
    *  public void setCurrentState(int state)
    *  public int getNextState()
    *  public void setNextState(int nextState)
    *  public int countNeighborsInState(int state)
    *  public List<T> getNeighbors()
    *  public Point getLocation()
    *  public List<Point> getVertices()
    *  public Point getCentroid()
    *  public void setNeighborhood(List<T> neighborhood)
    *  public double distance(T cell)
    *  public boolean equals(Object other)
    *  public int hashCode()
    *  public void setParams(Map<String, Double> newParameters)
* Grid: WarpedGrid
    *  public int getNumRows()
    *  public int getNumCols()
    *  public Iterator<T> iterator()
    *  public boolean vertexEqual(Point vtx1, Point vtx2)
* Simulation: FallingSandSimulation, WatorSimulation, SchellingSimulation, PercolationSimulation, ...
* Neighborhood

### Internal

* Identified Classes/Methods:
  No protected classes or methods, most of this funcionality is external


## Wish Model API

### External

* Goals:
  Minimal external access, keep method functionality protected or private

* Abstractions and their Methods:
  Changing parameters, updating grid state, updating cell shape

* Services and their Contract
  Update simulation according to user input, do not alter core functionality in determinging next states


### Internal

* Goals:
    * Less methods that are public - more protected or private  methods that hide the implementation details.

* Abstractions and their Methods
    * Grid and simulation class with more utilities hidden and protected methods to support these functionalities.
    * Create cell, pass cell, change cell, etc.

* Services and their Contract
    * Update grid and simulation pass data to model.

## API Task Description

### External

* English: The external API intends to create handle all core simulation features

* Java: Classes include Cell, Simulation, Shape, Grid

### Internal

* English: The internal API handle how the features are interconnected and functioning.

* Java: Classes include Neighborhood
 