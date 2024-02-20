//user requests help documentation
public Map<String, String> loadHelpFile(String fileName) {
  Map<String, String> helpMap = new HashMap<>();
  try {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(fileName);

    doc.getDocumentElement().normalize();

    Element root = doc.getDocumentElement();

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
    throw new IOException("Error loading help file", e);
  }
}

//save session
public void saveSession(Session session, String fileName) {
  try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
    List<String> commandHistory = session.getCommandHistory();
    for (String command : commandHistory) {
      writer.write(command);
      writer.newLine();
    }
  } catch (IOException e) {
    throw new IOException("Error saving session", e);
  }
}

//add a user-defined command to the help list
public void addUserDefCommandToHelpList(String command, String fileName) {
  try {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(fileName);

    doc.getDocumentElement().normalize();

    Element root = doc.getDocumentElement();

    Element helpEntry = doc.createElement("help_entry");

    Element commandElement = doc.createElement("command");
    commandElement.setTextContent(command);

    helpEntry.appendChild(commandElement);

    root.appendChild(helpEntry);

    writeXmlToFile(doc, fileName);
  } catch (Exception e) {
    throw new IOException("Error adding user defined command " + command + "to help list", e);
  }
}

//user requests to load a session with a non .slogo file
public Session loadSession(String fileName) {
  if (!fileName.endsWith(".slogo")) {
    throw new IllegalArgumentException("Invalid file format. Only .slogo files are supported.");
  }

  StringBuilder sb = new StringBuilder();
  try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line).append("\n");
    }
  } catch (IOException e) {
    throw new IOException("Error reading program file: " + fileName, e);
  }

  String commands = sb.toString();

  Session session = new Session(commands);

  return session;

}