package slogo.model.coderunner;

import java.util.HashMap;
import java.util.Map;
import slogo.model.api.exception.coderunner.RunCodeError;
import slogo.model.command.Command;
import slogo.model.command.UserCommand;

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

  Map<String, Double> getLocalVariables() {
    return new HashMap<>(variableMap);
  }

  Map<String, Map<String, String>> getLocalCommands() {
    Map<String, Map<String, String>> commands = new HashMap<>();
    for (Map.Entry<String,Command> entry : commandMap.entrySet()) {
      if (!(entry.getValue() instanceof UserCommand command)) {
        continue;
      }
      Map<String, String> metadata = new HashMap<>();
      metadata.put("arity", Integer.toString(command.getArity()));
      for (int i = 0; i < command.getArity(); i++) {
        metadata.put("parameter" + (i + 1), command.getParameters().get(i));
      }
      metadata.put("body", command.getBodySource());
      commands.put(entry.getKey(), metadata);
    }
    return commands;
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
