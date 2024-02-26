package slogo.model.api;

import java.util.ArrayList;
import java.util.HashMap;
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

  /**
   * Load a session from saved information in an XML file
   *
   * @param fileName The name of the XML file with saved session information
   * @return Session with state specified in XML file
   */
  public Session loadSession(String fileName) throws XmlException {
    //TODO: Implement method
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document doc = documentBuilder.parse(fileName);

      Element root = doc.getDocumentElement();
      root.normalize();
      return null;
    } catch (Exception e) {
      throw new XmlException(fileName);
    }
  }

  /**
   * Save a session to an XML file
   *
   * @param session  The session to be saved
   * @param fileName The name of the XML file to save the session information to
   */
  void saveSession(Session session, String fileName) {

    //TODO: Implement method
  }

  private String createCommandInfo(Element element) {
    NodeList commandList = element.getElementsByTagName("name");
    String commandInfo = "name: " + commandList.item(0).getTextContent().trim() + "\n";

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
      NodeList tagContentList = element.getElementsByTagName(tagName);
      String tagContent = tagContentList.item(0).getTextContent().trim();

      helpDoc = helpDoc + tagName + ": " + tagContent + "\n";
    }
    helpDoc = commandInfo + "\n" + helpDoc;
    return helpDoc;
  }

  private ArrayList<String> createTagList() {
    ArrayList<String> tagList = new ArrayList<>();
    tagList.add("alias");
    tagList.add("description");
    tagList.add("example");
    tagList.add("return");
    return tagList;
  }
}
