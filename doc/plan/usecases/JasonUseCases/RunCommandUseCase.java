import java.util.List;

/**
 * SLOGO-10: Set the heading of all the given turtles
 */
public class RunCommandUseCase {

  public static void main(String[] args) {
    Turtle turtle = new Turtle();
    Command setHeading = new setHeadingCommand();
    setHeading.run(List.of(turtle), List.of("25"));
  }

  private static class Turtle {

    int currentHeading = 25;

    int setHeading(int newHeading) {
      return Math.abs(newHeading - currentHeading);
    }
  }

  private static abstract class Command {

    void run(List<Turtle> turtles, List<String> args) {
      turtles.forEach(turtle -> doCommand(turtle, args));
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
