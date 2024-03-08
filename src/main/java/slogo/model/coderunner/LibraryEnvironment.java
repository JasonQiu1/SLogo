package slogo.model.coderunner;

import java.util.List;
import java.util.Random;
import slogo.model.api.exception.coderunner.ErrorType;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.command.JavaCommand;
import slogo.model.command.library.Back;
import slogo.model.command.library.Forward;
import slogo.model.command.library.Heading;
import slogo.model.command.library.Home;
import slogo.model.command.library.Id;
import slogo.model.command.library.Left;
import slogo.model.command.library.Right;
import slogo.model.command.library.SetHeading;
import slogo.model.command.library.SetXY;
import slogo.model.command.library.Towards;
import slogo.model.command.library.XCor;
import slogo.model.command.library.YCor;

public class LibraryEnvironment extends Environment {

  Random random;

  public LibraryEnvironment() {
    super(null);
    defineLibraryCommands();
    random = new Random();
  }

  private void defineLibraryCommands() {
    defineCommand("forward", new Forward());
    defineAlias("fd", "forward");
    defineCommand("right", new Right());
    defineAlias("rt", "right");
    defineCommand("back", new Back());
    defineAlias("bk", "back");
    defineCommand("left", new Left());
    defineAlias("lt", "left");
    defineCommand("setxy", new SetXY());
    defineAlias("goto", "setxy");
    defineCommand("home", new Home());
    defineCommand("setheading", new SetHeading());
    defineAlias("seth", "setheading");
    defineCommand("towards", new Towards());
    defineCommand("id", new Id());
    defineCommand("xcor", new XCor());
    defineCommand("ycor", new YCor());
    defineCommand("heading", new Heading());
    defineCommand("sum", new JavaCommand(List.of("a", "b"), args -> args.get(0) + args.get(1)));
    defineCommand("difference",
        new JavaCommand(List.of("a", "b"), args -> args.get(0) - args.get(1)));
    defineCommand("product", new JavaCommand(List.of("a", "b"), args -> args.get(0) * args.get(1)));
    defineCommand("quotient", new JavaCommand(List.of("a", "b"), args -> {
      if (args.get(1) == 0) {
        throw new RunCodeError(ErrorType.RUNTIME, "divideByZero", -1, "library command");
      }
      return args.get(0) / args.get(1);
    }));
    defineCommand("remainder", new JavaCommand(List.of("a", "b"), args -> {
      if (args.get(1) == 0) {
        throw new RunCodeError(ErrorType.RUNTIME, "moduloByZero", -1, "library command");
      }
      return args.get(0) % args.get(1);
    }));
    defineCommand("minus", new JavaCommand(List.of("x"), args -> -args.get(0)));
    defineCommand("random",
        new JavaCommand(List.of("max"), args -> random.nextDouble(args.get(0))));
    defineAlias("rand", "random");
    defineCommand("randomrange", new JavaCommand(List.of("min", "max"),
        args -> random.nextDouble(args.get(0), args.get(1) + Double.MIN_VALUE)));
    defineAlias("randr", "randomrange");
    defineCommand("sine",
        new JavaCommand(List.of("degrees"), args -> Math.sin(Math.toRadians(args.get(0)))));
    defineAlias("sin", "sine");
    defineCommand("cosine",
        new JavaCommand(List.of("degrees"), args -> Math.cos(Math.toRadians(args.get(0)))));
    defineAlias("cos", "cosine");
    defineCommand("tangent",
        new JavaCommand(List.of("degrees"), args -> Math.tan(Math.toRadians(args.get(0)))));
    defineAlias("tan", "tangent");
    defineCommand("arctangent", new JavaCommand(List.of("degrees"), args -> {
      if (args.get(0) == 0) {
        throw new RunCodeError(ErrorType.RUNTIME, "arctangentByZero", -1, "library command");
      }
      return Math.toDegrees(Math.atan(Math.toRadians(args.get(0))));
    }));
    defineAlias("atan", "arctangent");
    defineCommand("squareroot", new JavaCommand(List.of("x"), args -> {
      if (args.get(0) < 0) {
        throw new RunCodeError(ErrorType.RUNTIME, "sqrtByNegativeNumber", -1, "library command");
      }
      return Math.sqrt(args.get(0));
    }));
    defineAlias("sqrt", "squareroot");
    defineCommand("log", new JavaCommand(List.of("x"), args -> {
      if (args.get(0) <= 0) {
        throw new RunCodeError(ErrorType.RUNTIME, "logByNonPositiveNumber", -1, "library command");
      }
      return Math.log(args.get(0));
    }));
    defineCommand("power",
        new JavaCommand(List.of("base", "exponent"), args -> Math.pow(args.get(0), args.get(1))));
    defineAlias("pow", "power");
    defineCommand("pi", new JavaCommand(List.of(), args -> Math.PI));
    defineCommand("equal?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0).equals(args.get(1)))));
    defineCommand("notequal?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(!(args.get(0).equals(args.get(1))))));
    defineCommand("lessequal?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) <= args.get(1))));
    defineCommand("greaterequal?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) >= args.get(1))));
    defineCommand("less?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) < args.get(1))));
    defineCommand("greater?",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) > args.get(1))));
    // TODO: change to keywords and enable shortcircuiting
    defineCommand("and",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) != 0 && args.get(1) != 0)));
    defineCommand("or",
        new JavaCommand(List.of("a", "b"), args -> toDouble(args.get(0) != 0 || args.get(1) != 0)));
    defineCommand("not", new JavaCommand(List.of("x"), args -> toDouble(args.get(0) == 0)));
  }

  private double toDouble(boolean bool) {
    return bool ? 1 : 0;
  }

  private double headingToRadians(double heading) {
    return Math.toRadians(heading + 90);
  }

  private void defineAlias(String alias, String commandName) {
    defineCommand(alias, lookupCommand(new Token(TokenType.COMMAND, commandName, -1,
        "autogenerated by library environment loader")));
  }
}
