import java.util.List;

/**
 * SLOGO-10: Set the heading of all the given turtles, but not given enough arguments
 */
public class RunCommandNegativeUseCase {

  public static void main(String[] args) {
    Turtle turtle = new Turtle();
    Command setHeading = new setHeadingCommand();
    try {
      setHeading.run(List.of(turtle), List.of("25"));
    } catch (RunCommandException exception) {
      System.out.println("Handle this exception");
    }
  }

  private static class Turtle {

    int currentHeading = 25;

    int setHeading(int newHeading) {
      return Math.abs(newHeading - currentHeading);
    }
  }

  private static abstract class RunProgramException extends RuntimeException {

  }

  private static class RunCommandException extends RunProgramException {

  }

  private static abstract class Command {

    void run(List<Turtle> turtles, List<String> args) throws RunCommandException {
      try {
        turtles.forEach(turtle -> doCommand(turtle, args));
      } catch (RuntimeException e) {
        throw new RunCommandException();
      }
    }

    abstract int doCommand(Turtle turtle, List<String> args);
  }

  private static class setHeadingCommand extends Command {

    @Override
    int doCommand(Turtle turtle, List<String> args) {
      return turtle.setHeading(Integer.parseInt(args.get(0)));
    }
  }
}
