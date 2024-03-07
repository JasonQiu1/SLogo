package slogo.model.api;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

  /**
   * Load a session from saved information in an XML file
   *
   * @param fileName The name of the XML file with saved session information
   * @return Session with state specified in XML file
   */
  public Session loadSession(String fileName) throws XmlException {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document doc = documentBuilder.parse(fileName);

      Element root = doc.getDocumentElement();
      root.normalize();

      NodeList commandList = root.getElementsByTagName(commandTag);
      Session newSession = new Session();

      for (int currIndex = 0; currIndex < commandList.getLength(); currIndex++) {
        newSession.run(commandList.item(currIndex).getTextContent());
      }

      return newSession;

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
  public void saveSession(Session session, String fileName) throws XmlException {
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

      Document document = documentBuilder.newDocument();
      Element root = document.createElement("session");
      document.appendChild(root);

      Element commandHistory = document.createElement("command_history");
      root.appendChild(commandHistory);

      for (Map<String, Map<String, String>> commandMap : session.getCommandHistory(0)) {
        for (Entry<String, Map<String, String>> command : commandMap.entrySet()) {
          createCommandEntry(command, document, commandHistory);
        }
      }

      File file = new File(fileName + ".slogo");
      FileOutputStream fos = new FileOutputStream(file);
      javax.xml.transform.TransformerFactory.newInstance().newTransformer()
          .transform(new javax.xml.transform.dom.DOMSource(document),
              new javax.xml.transform.stream.StreamResult(fos));
      fos.close();
    } catch (Exception e) {
      throw new XmlException(fileName);
    }
  }

  private void createCommandEntry(Entry<String, Map<String, String>> command, Document document,
      Element commandHistory) {
    Element commandEntry = document.createElement("command_entry");
    commandHistory.appendChild(commandEntry);

    Element commandElement = document.createElement(commandTag);
    commandElement.setTextContent(command.getKey());
    commandEntry.appendChild(commandElement);

    Element metaDatEntryElement = document.createElement("meta_data_entry");
    commandEntry.appendChild(metaDatEntryElement);

    for (Entry<String, String> metaData : command.getValue().entrySet()) {
      createMetaDataEntry(metaData, document, metaDatEntryElement);
    }
  }

  private void createMetaDataEntry(Entry<String, String> metaData, Document document,
      Element metaDataEntryElement) {
    Element metaDataTitle = document.createElement("meta_data_title");
    metaDataTitle.setTextContent(metaData.getKey());
    metaDataEntryElement.appendChild(metaDataTitle);

    Element metaDataContent = document.createElement("meta_data_content");
    metaDataContent.setTextContent(metaData.getValue());
    metaDataEntryElement.appendChild(metaDataContent);
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
}
