package slogo.model.coderunner;

import slogo.model.command.library.Back;
import slogo.model.command.library.Forward;
import slogo.model.command.library.Heading;
import slogo.model.command.library.Id;
import slogo.model.command.library.Left;
import slogo.model.command.library.Right;
import slogo.model.command.library.SetHeading;
import slogo.model.command.library.SetXY;
import slogo.model.command.library.Towards;
import slogo.model.command.library.XCor;
import slogo.model.command.library.YCor;

public class LibraryEnvironment extends Environment {

  public LibraryEnvironment() {
    super(null);
    defineLibraryCommands();
  }

  private void defineLibraryCommands() {
    defineCommand("forward", new Forward());
    defineCommand("fd", new Forward());
    defineCommand("right", new Right());
    defineCommand("rt", new Right());
    defineCommand("back", new Back());
    defineCommand("bk", new Back());
    defineCommand("left", new Left());
    defineCommand("lt", new Left());
    defineCommand("setxy", new SetXY());
    defineCommand("goto", new SetXY());
    defineCommand("setheading", new SetHeading());
    defineCommand("seth", new SetHeading());
    defineCommand("towards", new Towards());
    defineCommand("id", new Id());
    defineCommand("xcor", new XCor());
    defineCommand("ycor", new YCor());
    defineCommand("heading", new Heading());
  }
}
