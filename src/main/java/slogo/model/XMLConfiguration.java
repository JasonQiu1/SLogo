package slogo.model;

import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import slogo.exception.XMLException;

/**
 * The purpose of this class is to parse XML files for command help information and information
 * needed to load a session.  Additionally, this class will create XML files intended to save the
 * current state of a session.
 */
public class XMLConfiguration {

  /**
   * Constructor for XmlConfig
   */
  public XMLConfiguration() {
  }

  /**
   * Parse help information from an XML file containing information on library commands
   *
   * @param fileName the name of the XML file
   * @return Command names mapped to help descriptions
   */
  public Map<String, String> loadHelpFile(String fileName) throws XMLException {
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

        NodeList commandList = element.getElementsByTagName("command");
        String command = commandList.item(0).getTextContent();

        NodeList helpDocList = element.getElementsByTagName("help_doc");
        String helpDoc = helpDocList.item(0).getTextContent();

        helpMap.put(command, helpDoc);
      }
      return helpMap;
    } catch (Exception e) {
      throw new XMLException(fileName);
    }
  }

  /**
   * Load a session from saved information in an XML file
   *
   * @param fileName The name of the XML file with saved session information
   * @return Session with state specified in XML file
   */
  Session loadSession(String fileName) {
    //TODO: Implement method
    return null;
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
}
