package slogo.model.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import slogo.model.api.exception.XmlException;

/**
 * The purpose of this class is to parse XML files for command help information and information
 * needed to load a session.  Additionally, this class will create XML files intended to save the
 * current state of a session.
 */
public class XmlConfiguration {

  private final ArrayList<String> tagList;
  private final String commandTag = "command";

  /**
   * Constructor for XmlConfig
   */
  public XmlConfiguration() {
    tagList = createTagList();
  }

  /**
   * Parse help information from an XML file containing information on library commands
   *
   * @param fileName the name of the XML file
   * @return Command names mapped to help descriptions
   */
  public Map<String, String> loadHelpFile(String fileName) throws XmlException {
    Map<String, String> helpMap = new HashMap<>();
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document doc = documentBuilder.parse(fileName);

      Element root = doc.getDocumentElement();
      root.normalize();

      NodeList nodeList = root.getElementsByTagName("help_entry");

      for (int i = 0; i < nodeList.getLength(); i++) {
        Element element = (Element) nodeList.item(i);

        String commandInfo = createCommandInfo(element);

        String helpDoc = createHelpDocumentation(element, commandInfo);

        helpMap.put(commandInfo, helpDoc);
      }
      return helpMap;
    } catch (Exception e) {
      throw new XmlException(fileName);
    }
  }

  @Deprecated
  public Session loadSession(String fileName) throws XmlException {
    try {
      List<String> commands = getCommandsFromFile(fileName);
      Session session = new Session();

      for (String command : commands) {
        session.run(command);
      }

      return session;
    } catch (IOException e) {
      throw new XmlException(fileName);
    }
  }

  /**
   * Load a session from saved information in an XML file
   *
   * @param fileName The name of the XML file with saved session information
   * @return Session with state specified in XML file
   */
  public List<String> loadSessionFromFile(String fileName) throws XmlException {
    try {
      List<String> commands = getCommandsFromFile(fileName);

      return commands;
    } catch (IOException e) {
      throw new XmlException(fileName);
    }
  }

  /**
   * Save a session to an XML file
   *
   * @param session  The session to be saved
   * @param fileName The name of the XML file to save the session information to
   */
  public void saveSession(Session session, String fileName) throws XmlException {
    File file = new File(fileName + ".slogo");

    try {
      PrintWriter writer = new PrintWriter(file);
      for (Map<String, Map<String, String>> commandMap : session.getCommandHistory(0)) {
        for (String command : commandMap.keySet()) {
          writer.println(command);
        }
      }
      writer.close();
    } catch (Exception e) {
      throw new XmlException(fileName);
    }
  }

  private String createCommandInfo(Element element) {
    NodeList commandList = element.getElementsByTagName("name");
    String commandInfo = "Name: " + commandList.item(0).getTextContent().trim() + "\n";

    commandInfo = commandInfo + "Parameter(s): ";

    NodeList parameterList = element.getElementsByTagName("parameters");

    if (parameterList.getLength() == 0) {
      commandInfo = commandInfo + "none";
    } else {
      for (int i = 0; i < parameterList.getLength(); i++) {
        String parameter = parameterList.item(i).getTextContent().trim();
        commandInfo = commandInfo + parameter;
        if (i != parameterList.getLength() - 1) {
          commandInfo = commandInfo + ", ";
        }
      }
    }
    return commandInfo;
  }

  private String createHelpDocumentation(Element element, String commandInfo) {
    String helpDoc = "";
    for (String tagName : tagList) {
      NodeList tagContentList = element.getElementsByTagName(tagName.toLowerCase());
      String tagContent = tagContentList.item(0).getTextContent().trim();

      helpDoc = helpDoc + tagName + ": " + tagContent + "\n";
    }
    helpDoc = commandInfo + "\n" + helpDoc;
    return helpDoc;
  }

  private ArrayList<String> createTagList() {
    ArrayList<String> tagList = new ArrayList<>();
    tagList.add("Alias");
    tagList.add("Description");
    tagList.add("Example");
    tagList.add("Return");
    return tagList;
  }

  private List<String> getCommandsFromFile(String fileName) throws IOException {
    List<String> commands = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String line;
    int numOpenBrackets = 0;
    boolean append = false;

    while ((line = reader.readLine()) != null) {
      if (!line.startsWith("#") && !line.isEmpty()) {
        line = line.trim();
        parseLine(line.trim(), commands, append, numOpenBrackets);
        numOpenBrackets += determineNumNewOpenBrackets(line);
        append = determineAppend(append, line, numOpenBrackets);
      }
    }
    return commands;
  }

  private boolean determineAppend(Boolean append, String line, int numOpenBrackets) {
    if (line.startsWith("[") || line.endsWith("[")) {
      return true;
    } else if (line.startsWith("]") && numOpenBrackets == 0) {
      return false;
    } else {
      return append;
    }
  }

  private int determineNumNewOpenBrackets(String line) {
    if (line.startsWith("[") || line.endsWith("[")) {
      return 1;
    } else if (line.startsWith("]")) {
      return -1;
    } else {
      return 0;
    }
  }

  private void parseLine(String line, List<String> commands, boolean append, int numOpenBrackets) {
    int index = commands.size() - 1;
    if (line.startsWith("[")) {
      commands.set(index, commands.get(index) + " [");
    } else if (line.endsWith("[")) {
      if (numOpenBrackets > 0) {
        commands.set(index, commands.get(index) + " " + line);
      } else {
        commands.add(line);
      }
    } else if (line.startsWith("]")) {
      commands.set(index, commands.get(index) + "]");
    } else {
      if (append) {
        commands.set(index, commands.get(index) + line + " ");
      } else {
        commands.add(line);
      }
    }
  }
}