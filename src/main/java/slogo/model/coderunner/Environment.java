package slogo.model.coderunner;

import java.util.HashMap;
import java.util.Map;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.command.Command;

/**
 * Represents an environment frame in the Slogo environment which supports closure for variables and
 * commands.
 *
 * @author Jason Qiu
 */
class Environment {

  Environment(Environment parent) {
    this.parent = parent;
    this.commandMap = new HashMap<>();
    this.variableMap = new HashMap<>();
  }

  double lookupVariable(Token variableName) throws RunCodeError {
    if (!variableMap.containsKey(variableName.literal())) {
      if (parent == null) {
        throw Util.createError("variableNotFound", variableName);
      }
      return parent.lookupVariable(variableName);
    }
    return variableMap.get(variableName.literal());
  }

  Command lookupCommand(Token commandName) throws RunCodeError {
    if (!commandMap.containsKey(commandName.literal())) {
      if (parent == null) {
        throw Util.createError("commandNotFound", commandName);
      }
      return parent.lookupCommand(commandName);
    }
    return commandMap.get(commandName.literal());
  }

  void setVariable(String name, double value) {
    variableMap.put(name, value);
  }

  void defineCommand(String name, Command command) {
    commandMap.put(name, command);
  }

  private final Environment parent;
  private final Map<String, Command> commandMap;
  private final Map<String, Double> variableMap;
}
