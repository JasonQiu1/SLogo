# API Changes

#### Deprecated loadSession command

In the changes stage of the project, we realized that implementing session reassignment violated the
dependencies that we had created between listeners and controllers. Listeners hold the session, but
calling loadSession in the controller required the session to be reassigned from the controller side
of the relationship. Therefore, we felt it made the most sense to deprecate the loadSession method
that returned a session. Having the method return the commands allowed us to
maintain backward compatibility while encouraging a more cohesive design where controllers do not
reassign the session.

#### New `resetFrame(int frames)` method

In the changes stage of the project, we realized that, in order to replay the most recent animation
from the most recent command, we would need to pass to `TurtleAnimator` the number of frames ran for
the most recent command. Only by doing so will the TurtleAnimator know the point in state history to
go back to for each Turtle. However, to avoid deprecating the existing `resetFrame()` method which
resets the turtle animation to the beginning and replays all commands, we chose to use method
overload and added a new method `resetFrame(int frames)` that accepts a parameter.

# Session

We realized the view didn't have any way to know how many steps each turtle took after 
a string of commands were run, so it couldn't accurately query the right amount of step history to
animate.

To solve this, Session.run's signature was changed to return an integer corresponding to the number
of steps each turtle performed after running the given string of commands.